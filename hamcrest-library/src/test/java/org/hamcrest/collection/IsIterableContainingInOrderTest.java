package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsEqual.equalTo;

@SuppressWarnings("unchecked")
public class IsIterableContainingInOrderTest extends AbstractMatcherTest {
    // temporary hack until the Java type system works
    private final Matcher<Iterable<? extends WithValue>> contains123 = contains(value(1), value(2), value(3));

    @Override
    protected Matcher<?> createMatcher() {
        return contains(1, 2);
    }

    public void testMatchingSingleItemIterable() throws Exception {
        assertMatches("Single item iterable", contains(1), asList(1));
    }

    public void testMatchingMultipleItemIterable() throws Exception {
        assertMatches("Multiple item iterable", contains(1, 2, 3), asList(1, 2, 3));
    }

    public void testDoesNotMatchWithMoreElementsThanExpected() throws Exception {
        assertMismatchDescription("Not matched: <4>", contains(1, 2, 3), asList(1, 2, 3, 4));
    }

    public void testDoesNotMatchWithFewerElementsThanExpected() throws Exception {
        List<WithValue> valueList = asList(make(1), make(2));
        assertMismatchDescription("No item matched: value with <3>", contains123, valueList);
    }

    public void testDoesNotMatchIfSingleItemMismatches() throws Exception {
        assertMismatchDescription("item 0: value was <3>", contains(value(4)), asList(make(3)));
    }

    public void testDoesNotMatchIfOneOfMultipleItemsMismatch() throws Exception {
        assertMismatchDescription("item 2: value was <4>", contains123, asList(make(1), make(2), make(4)));
    }

    public void testDoesNotMatchEmptyIterable() throws Exception {
        assertMismatchDescription("No item matched: value with <4>", contains(value(4)), new ArrayList<WithValue>());
    }

    public void testHasAReadableDescription() {
        assertDescription("iterable containing [<1>, <2>]", contains(1, 2));
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
