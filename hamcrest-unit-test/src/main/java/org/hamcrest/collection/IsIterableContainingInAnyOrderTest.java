package org.hamcrest.collection;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsIterableContainingInAnyOrderTest extends AbstractMatcherTest<Iterable<Number>> {

    @Override
    protected Matcher<Iterable<Number>> createMatcher() {
        return containsInAnyOrder(1, 2);
    }   

    public void testMatchesSingleItemIterable() {
      assertMatches("single item", containsInAnyOrder(1), asList(1));
    }

    public void testDoesNotMatchEmpty() {
        assertMismatchDescription("No item matches: <1>, <2> in []", containsInAnyOrder(1, 2), emptyList());
    }
    
    public void testMatchesIterableOutOfOrder() {
        assertMatches("Out of order", containsInAnyOrder(1, 2), asList(2, 1));
    }
    
    public void testMatchesIterableInOrder() {
        assertMatches("In order", containsInAnyOrder(1, 2), asList(1, 2));
    }
    
    public void testDoesNotMatchIfOneOfMultipleElementsMismatches() {
        assertMismatchDescription("Not matched: <4>", containsInAnyOrder(1, 2, 3), asList(1, 2, 4));
    }

    public void testDoesNotMatchIfThereAreMoreElementsThanMatchers() {
        assertMismatchDescription("Not matched: <2>", containsInAnyOrder(1, 3), asList(1, 2, 3));
    }
    
    public void testDoesNotMatchIfThereAreMoreMatchersThanElements() {
        assertMismatchDescription("No item matches: <4> in [<1>, <2>, <3>]", containsInAnyOrder(1, 2, 3, 4), asList(1, 2, 3));
    }

    public void testHasAReadableDescription() {
        assertDescription("iterable over [<1>, <2>] in any order", containsInAnyOrder(1, 2));
    }
}
