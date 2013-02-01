package org.hamcrest.collection;

import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsArrayContainingInAnyOrderTest extends AbstractMatcherTest {

    @SuppressWarnings("unchecked")
    @Override
    protected Matcher<?> createMatcher() {
        return arrayContainingInAnyOrder(equalTo(1), equalTo(2));
    }

    @SuppressWarnings("unchecked")
    public void testHasAReadableDescription() {
        assertDescription("array with elements [<1>, <2>] in any order", arrayContainingInAnyOrder(equalTo(1), equalTo(2)));
        assertDescription("array with elements [<1>, <2>] in any order", arrayContainingInAnyOrder(1, 2));
    }
    
    public void testMatchesItemsInAnyOrder() {
      assertMatches("in order", arrayContainingInAnyOrder(1, 2, 3), new Integer[] {1, 2, 3});
      assertMatches("out of order", arrayContainingInAnyOrder(1, 2, 3), new Integer[] {3, 2, 1});
      assertMatches("single", arrayContainingInAnyOrder(1), new Integer[] {1});
    }

    @SuppressWarnings("unchecked")
    public void testAppliesMatchersInAnyOrder() {
      assertMatches("in order", arrayContainingInAnyOrder(equalTo(1), equalTo(2), equalTo(3)), new Integer[] {1, 2, 3});
      assertMatches("out of order", arrayContainingInAnyOrder(equalTo(1), equalTo(2), equalTo(3)), new Integer[] {3, 2, 1});
      assertMatches("single", arrayContainingInAnyOrder(equalTo(1)), new Integer[] {1});
    }

    public void testMismatchesItemsInAnyOrder() {
      Matcher<Integer[]> matcher = arrayContainingInAnyOrder(1, 2, 3);
      assertMismatchDescription("was null", matcher, null);
      assertMismatchDescription("array contained <0> elements", matcher, new Integer[] {});
      assertMismatchDescription("array contained <1> elements", matcher, new Integer[] {1});
      assertMismatchDescription("array contained <4> elements", matcher, new Integer[] {4,3,2,1});
      assertMismatchDescription("element <1> was <4>", matcher, new Integer[] {3,4,2});
    }
}
