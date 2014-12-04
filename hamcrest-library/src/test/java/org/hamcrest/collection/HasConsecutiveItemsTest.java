package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.HasConsecutiveItems.hasConsecutiveItems;
import static org.hamcrest.core.IsEqual.equalTo;

@SuppressWarnings("unchecked")
public class HasConsecutiveItemsTest extends AbstractMatcherTest {
    private final Matcher<Collection<? extends WithValue>> contains123 = hasConsecutiveItems(value(1), value(2), value(3));

    @Override
    protected Matcher<?> createMatcher() {
        return hasConsecutiveItems(1, 2);
    }

    public void testMatchingSingleItemCollection() throws Exception {
        assertMatches("Single item iterable", hasConsecutiveItems(1), asList(1));
    }

    public void testMatchingMultipleItemCollection() throws Exception {
        assertMatches("Multiple item iterable", hasConsecutiveItems(1, 2, 3), asList(1, 2, 3));
    }

    public void testMatchesWithMoreElementsThanExpectedAtBeginning() throws Exception {
        assertMatches("More elements at beginning", hasConsecutiveItems(2, 3, 4), asList(1, 2, 3, 4));
    }

    public void testMatchesWithMoreElementsThanExpectedAtEnd() throws Exception {
        assertMatches("More elements at end", hasConsecutiveItems(1, 2, 3), asList(1, 2, 3, 4));
    }

    public void testDoesNotMatchWithMoreElementsThanExpectedInBetween() throws Exception {
        assertMismatchDescription("could not find items inside collection [<1>, <2>, <3>]", hasConsecutiveItems(1, 3), asList(1, 2, 3));
    }

    public void testMatchesSubSection() throws Exception {
        assertMatches("Sub section of iterable", hasConsecutiveItems(2, 3), asList(1, 2, 3, 4));
    }

    public void testDoesNotMatchWithFewerElementsThanExpected() throws Exception {
        List<WithValue> valueList = asList(make(1), make(2));
        assertMismatchDescription("could not find items inside collection [<WithValue 1>, <WithValue 2>]", contains123, valueList);
    }

    public void testDoesNotMatchIfSingleItemNotFound() throws Exception {
        assertMismatchDescription("could not find items inside collection [<WithValue 3>]", hasConsecutiveItems(value(4)), asList(make(3)));
    }

    public void testDoesNotMatchIfOneOfMultipleItemsNotFound() throws Exception {
        assertMismatchDescription("could not find items inside collection [<WithValue 1>, <WithValue 2>, <WithValue 4>]", contains123, asList(make(1), make(2), make(4)));
    }

    public void testDoesNotMatchEmptyCollection() throws Exception {
        assertMismatchDescription("could not find items inside collection []", hasConsecutiveItems(value(4)), new ArrayList<WithValue>());
    }

    public void testHasAReadableDescription() {
        assertDescription("collection contains consecutive items matching [<1>, <2>]", hasConsecutiveItems(1, 2));
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
