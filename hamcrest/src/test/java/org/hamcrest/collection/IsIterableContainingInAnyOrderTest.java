package org.hamcrest.collection;

import org.hamcrest.*;
import org.hamcrest.collection.IsIterableContainingInOrderTest.WithValue;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
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
        assertMismatchDescription("not enough items: []", containsInAnyOrder(1, 2), Collections.<Integer>emptyList());
    }
    
    public void testMatchesIterableOutOfOrder() {
        assertMatches("Out of order", containsInAnyOrder(1, 2), asList(2, 1));
    }
    
    public void testMatchesIterableInOrder() {
        assertMatches("In order", containsInAnyOrder(1, 2), asList(1, 2));
    }
    
    public void testDoesNotMatchIfOneOfMultipleElementsMismatches() {
        assertMismatchDescription("no permutation of the matchers matched the items sequence", containsInAnyOrder(1, 2, 3), asList(1, 2, 4));
    }

    @SuppressWarnings("unchecked")
    public void testDoesNotMatchIfThereAreMoreElementsThanMatchers() {
        final Matcher<Iterable<? extends WithValue>> helpTheCompilerOut = containsInAnyOrder(value(1), value(3));
        assertMismatchDescription("too many items: [<WithValue 1>, <WithValue 2>, <WithValue 3>]", helpTheCompilerOut, asList(make(1), make(2), make(3)));
    }
    
    public void testDoesNotMatchIfThereAreMoreMatchersThanElements() {
        assertMismatchDescription("not enough items: [<1>, <2>, <3>]", containsInAnyOrder(1, 2, 3, 4), asList(1, 2, 3));
    }

    public void testHasAReadableDescription() {
        assertDescription("iterable with items [<1>, <2>] in any order", containsInAnyOrder(1, 2));
    }

    private static Matcher<Integer> isEven() {
        return new TypeSafeMatcher<Integer>() {
            @Override
            protected boolean matchesSafely(Integer item) {
                return item % 2 == 0;
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("An even number");
            }
        };
    }

    private static Matcher<Integer> isOdd() {
        return not(isEven());
    }

    public void testAllPermutationsAreTried() {
        assertMatches(
                containsInAnyOrder(
                        containsString("a"),
                        containsString("b")
                ),
                asList("ab", "a"));
        assertMatches(
                containsInAnyOrder(
                        isOdd(), isEven(), equalTo(1)
                ),
                asList(1, 2, 3));
    }

}
