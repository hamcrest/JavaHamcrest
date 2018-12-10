package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.collection.ArrayMatching.arrayContainingInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;

public class ArrayMatchingInAnyOrderTest extends AbstractMatcherTest {

    @SuppressWarnings("unchecked")
    @Override
    protected Matcher<?> createMatcher() {
        return ArrayMatching.arrayContainingInAnyOrder(equalTo(1), equalTo(2));
    }

    @SuppressWarnings("unchecked")
    public void testHasAReadableDescription() {
        assertDescription("[<1>, <2>] in any order", ArrayMatching.arrayContainingInAnyOrder(equalTo(1), equalTo(2)));
        assertDescription("[<1>, <2>] in any order", ArrayMatching.arrayContainingInAnyOrder(1, 2));
    }
    
    public void testMatchesItemsInAnyOrder() {
      assertMatches("in order", ArrayMatching.arrayContainingInAnyOrder(1, 2, 3), new Integer[] {1, 2, 3});
      assertMatches("out of order", ArrayMatching.arrayContainingInAnyOrder(1, 2, 3), new Integer[] {3, 2, 1});
      assertMatches("single", ArrayMatching.arrayContainingInAnyOrder(1), new Integer[] {1});
    }

    @SuppressWarnings("unchecked")
    public void testAppliesMatchersInAnyOrder() {
      assertMatches("in order", ArrayMatching.arrayContainingInAnyOrder(equalTo(1), equalTo(2), equalTo(3)), new Integer[] {1, 2, 3});
      assertMatches("out of order", ArrayMatching.arrayContainingInAnyOrder(equalTo(1), equalTo(2), equalTo(3)), new Integer[] {3, 2, 1});
      assertMatches("single", ArrayMatching.arrayContainingInAnyOrder(equalTo(1)), new Integer[] {1});
    }

    public void testMismatchesItemsInAnyOrder() {
      Matcher<Integer[]> matcher = ArrayMatching.arrayContainingInAnyOrder(1, 2, 3);
      assertMismatchDescription("was null", matcher, null);
      assertMismatchDescription("no item matches: <1>, <2>, <3> in []", matcher, new Integer[] {});
      assertMismatchDescription("no item matches: <2>, <3> in [<1>]", matcher, new Integer[] {1});
      assertMismatchDescription("not matched: <4>", matcher, new Integer[] {4,3,2,1});
    }
}
