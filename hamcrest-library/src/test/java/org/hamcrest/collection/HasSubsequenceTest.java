package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.HasSubsequence.hasSubsequence;
import static org.hamcrest.core.IsEqual.equalTo;

@SuppressWarnings("unchecked")
public class HasSubsequenceTest extends AbstractMatcherTest {
    private final Matcher<Collection<? extends WithValue>> contains123 = hasSubsequence(value(1), value(2), value(3));

    @Override
    protected Matcher<?> createMatcher() {
        return hasSubsequence(1, 2);
    }

    public void testMatchingSingleItemCollection() throws Exception {
        assertMatches("Single item iterable", hasSubsequence(1), asList(1));
    }

    public void testMatchingMultipleItemCollection() throws Exception {
        assertMatches("Multiple item iterable", hasSubsequence(1, 2, 3), asList(1, 2, 3));
    }

    public void testMatchesWithMoreElementsThanExpectedAtBeginning() throws Exception {
        assertMatches("More elements at beginning", hasSubsequence(2, 3, 4), asList(1, 2, 3, 4));
    }

    public void testMatchesWithMoreElementsThanExpectedAtEnd() throws Exception {
        assertMatches("More elements at end", hasSubsequence(1, 2, 3), asList(1, 2, 3, 4));
    }

    public void testDoesNotMatchWithMoreElementsThanExpectedInBetween() throws Exception {
        assertMismatchDescription("could not find subsequence inside collection [<1>, <2>, <3>]", hasSubsequence(1, 3), asList(1, 2, 3));
    }

    public void testMatchesSubSection() throws Exception {
        assertMatches("Sub section of iterable", hasSubsequence(2, 3), asList(1, 2, 3, 4));
    }

    public void testDoesNotMatchWithFewerElementsThanExpected() throws Exception {
        List<WithValue> valueList = asList(make(1), make(2));
        assertMismatchDescription("could not find subsequence inside collection [<WithValue 1>, <WithValue 2>]", contains123, valueList);
    }

    public void testDoesNotMatchIfSingleItemNotFound() throws Exception {
        assertMismatchDescription("could not find subsequence inside collection [<WithValue 3>]", hasSubsequence(value(4)), asList(make(3)));
    }

    public void testDoesNotMatchIfOneOfMultipleItemsNotFound() throws Exception {
        assertMismatchDescription("could not find subsequence inside collection [<WithValue 1>, <WithValue 2>, <WithValue 4>]", contains123, asList(make(1), make(2), make(4)));
    }

    public void testDoesNotMatchEmptyCollection() throws Exception {
        assertMismatchDescription("could not find subsequence inside collection []", hasSubsequence(value(4)), new ArrayList<WithValue>());
    }

    public void testHasAReadableDescription() {
        assertDescription("collection contains subsequence matching [<1>, <2>]", hasSubsequence(1, 2));
    }

    public static class WithValue {
      private final int value;
      public WithValue(int value) { this.value = value; }
      public int getValue() { return value; }
      @Override public String toString() { return "WithValue " + value; }
    }

    public static WithValue make(int value) {
      return new WithValue(value);
    }

    public static Matcher<WithValue> value(int value) {
      return new FeatureMatcher<WithValue, Integer>(equalTo(value), "value with", "value") {
        @Override protected Integer featureValueOf(WithValue actual) { return actual.getValue(); }
      };
    }
}
