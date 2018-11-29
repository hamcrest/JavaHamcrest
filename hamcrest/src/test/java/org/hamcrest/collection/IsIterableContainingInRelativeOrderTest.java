package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInRelativeOrder.containsInRelativeOrder;
import static org.hamcrest.core.IsEqual.equalTo;

@SuppressWarnings("unchecked")
public class IsIterableContainingInRelativeOrderTest extends AbstractMatcherTest {
    // temporary hack until the Java type system works
    private final Matcher<Iterable<? extends WithValue>> contains123 = containsInRelativeOrder(value(1), value(2), value(3));

    @Override
    protected Matcher<?> createMatcher() {
        return containsInRelativeOrder(1, 2);
    }

    public void testMatchingSingleItemIterable() throws Exception {
        assertMatches("Single item iterable", containsInRelativeOrder(1), asList(1));
    }

    public void testMatchingMultipleItemIterable() throws Exception {
        assertMatches("Multiple item iterable", containsInRelativeOrder(1, 2, 3), asList(1, 2, 3));
    }

    public void testMatchesWithMoreElementsThanExpectedAtBeginning() throws Exception {
        assertMatches("More elements at beginning", containsInRelativeOrder(2, 3, 4), asList(1, 2, 3, 4));
    }

    public void testMatchesWithMoreElementsThanExpectedAtEnd() throws Exception {
        assertMatches("More elements at end", containsInRelativeOrder(1, 2, 3), asList(1, 2, 3, 4));
    }

    public void testMatchesWithMoreElementsThanExpectedInBetween() throws Exception {
        assertMatches("More elements in between", containsInRelativeOrder(1, 3), asList(1, 2, 3));
    }

    public void testMatchesSubSection() throws Exception {
        assertMatches("Sub section of iterable", containsInRelativeOrder(2, 3), asList(1, 2, 3, 4));
    }

    public void testMatchesWithSingleGapAndNotFirstOrLast() throws Exception {
        assertMatches("Sub section with single gaps without a first or last match", containsInRelativeOrder(2, 4), asList(1, 2, 3, 4, 5));
    }

    public void testMatchingSubSectionWithManyGaps() throws Exception {
        assertMatches("Sub section with many gaps iterable", containsInRelativeOrder(2, 4, 6), asList(1, 2, 3, 4, 5, 6, 7));
    }

    public void testDoesNotMatchWithFewerElementsThanExpected() throws Exception {
        List<WithValue> valueList = asList(make(1), make(2));
        assertMismatchDescription("value with <3> was not found after <WithValue 2>", contains123, valueList);
    }

    public void testDoesNotMatchIfSingleItemNotFound() throws Exception {
        assertMismatchDescription("value with <4> was not found", containsInRelativeOrder(value(4)), asList(make(3)));
    }

    public void testDoesNotMatchIfOneOfMultipleItemsNotFound() throws Exception {
        assertMismatchDescription("value with <3> was not found after <WithValue 2>", contains123, asList(make(1), make(2), make(4)));
    }

    public void testDoesNotMatchEmptyIterable() throws Exception {
        assertMismatchDescription("value with <4> was not found", containsInRelativeOrder(value(4)), new ArrayList<WithValue>());
    }

    public void testHasAReadableDescription() {
        assertDescription("iterable containing [<1>, <2>] in relative order", containsInRelativeOrder(1, 2));
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
