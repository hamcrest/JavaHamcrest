package org.hamcrest.collection;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrderTest.WithValue;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrderTest.make;
import static org.hamcrest.collection.IsIterableContainingInOrderTest.value;

public class IsIterableContainingInAnyOrderTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return containsInAnyOrder(1, 2);
    }

    @Test
    public void testMatchesSingleItemIterable() {
      assertMatches("single item", containsInAnyOrder(1), asList(1));
    }

    @Test
    public void testDoesNotMatchEmpty() {
        assertMismatchDescription("no item matches: <1>, <2> in []", containsInAnyOrder(1, 2), Collections.<Integer>emptyList());
    }

    @Test
    public void testMatchesIterableOutOfOrder() {
        assertMatches("Out of order", containsInAnyOrder(1, 2), asList(2, 1));
    }

    @Test
    public void testMatchesIterableInOrder() {
        assertMatches("In order", containsInAnyOrder(1, 2), asList(1, 2));
    }

    @Test
    public void testDoesNotMatchIfOneOfMultipleElementsMismatches() {
        assertMismatchDescription("not matched: <4>", containsInAnyOrder(1, 2, 3), asList(1, 2, 4));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDoesNotMatchIfThereAreMoreElementsThanMatchers() {
        final Matcher<Iterable<? extends WithValue>> helpTheCompilerOut = containsInAnyOrder(value(1), value(3));
        assertMismatchDescription("not matched: <WithValue 2>", helpTheCompilerOut, asList(make(1), make(2), make(3)));
    }

    @Test
    public void testDoesNotMatchIfThereAreMoreMatchersThanElements() {
        assertMismatchDescription("no item matches: <4> in [<1>, <2>, <3>]", containsInAnyOrder(1, 2, 3, 4), asList(1, 2, 3));
    }

    @Test
    public void testHasAReadableDescription() {
        assertDescription("iterable with items [<1>, <2>] in any order", containsInAnyOrder(1, 2));
    }

}
