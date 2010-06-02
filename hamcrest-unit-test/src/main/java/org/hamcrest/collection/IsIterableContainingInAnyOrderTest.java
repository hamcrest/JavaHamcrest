package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrderTest.WithValue;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrderTest.make;
import static org.hamcrest.collection.IsIterableContainingInOrderTest.value;

public class IsIterableContainingInAnyOrderTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return containsInAnyOrder(1, 2);
    }   

    public void testMatchesSingleItemIterable() {
      assertMatches("single item", containsInAnyOrder(1), asList(1));
    }

    public void testDoesNotMatchEmpty() {
        assertMismatchDescription("No item matches: <1>, <2> in []", containsInAnyOrder(1, 2), Collections.<Integer>emptyList());
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

    @SuppressWarnings("unchecked")
    public void testDoesNotMatchIfThereAreMoreElementsThanMatchers() {
        Matcher<Iterable<? extends WithValue>> helpTheCompilerOut = containsInAnyOrder(value(1), value(3));
        assertMismatchDescription("Not matched: <WithValue 2>", helpTheCompilerOut, asList(make(1), make(2), make(3)));
    }
    
    public void testDoesNotMatchIfThereAreMoreMatchersThanElements() {
        assertMismatchDescription("No item matches: <4> in [<1>, <2>, <3>]", containsInAnyOrder(1, 2, 3, 4), asList(1, 2, 3));
    }

    public void testHasAReadableDescription() {
        assertDescription("iterable over [<1>, <2>] in any order", containsInAnyOrder(1, 2));
    }
    
    public void testDoesNotMatchIfSingleItemMismatches() throws Exception {
      assertMismatchDescription("Not matched: <WithValue 3>", containsInAnyOrder(value(4)), asList(make(3)));  
  }
  

}
