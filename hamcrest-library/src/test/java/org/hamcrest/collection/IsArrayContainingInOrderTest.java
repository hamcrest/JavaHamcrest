package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsArrayContainingInOrderTest extends AbstractMatcherTest {

    @SuppressWarnings("unchecked")
    @Override
    protected Matcher<?> createMatcher() {
        return arrayContaining(equalTo(1), equalTo(2));
    }

    @SuppressWarnings("unchecked")
    public void testHasAReadableDescription() {
        assertDescription("[<1>, <2>]", arrayContaining(equalTo(1), equalTo(2)));
    }
    
    public void testMatchesItemsInOrder() {
      assertMatches("in order", arrayContaining(1, 2, 3), new Integer[] {1, 2, 3});
      assertMatches("single", arrayContaining(1), new Integer[] {1});
    }

    @SuppressWarnings("unchecked")
    public void testAppliesMatchersInOrder() {
      assertMatches("in order", arrayContaining(equalTo(1), equalTo(2), equalTo(3)), new Integer[] {1, 2, 3});
      assertMatches("single", arrayContaining(equalTo(1)), new Integer[] {1});
    }
    
    public void testMismatchesItemsInOrder() {
      Matcher<Integer[]> matcher = arrayContaining(1, 2, 3);
      assertMismatchDescription("was null", matcher, null);
      assertMismatchDescription("no item was <1>", matcher, new Integer[] {});
      assertMismatchDescription("no item was <2>", matcher, new Integer[] {1});
      assertMismatchDescription("item 0: was <4>", matcher, new Integer[] {4,3,2,1});
      assertMismatchDescription("item 2: was <4>", matcher, new Integer[] {1,2, 4});
    }

    public void testCanHandleNullValuesInAnArray() {
      assertMatches("with nulls", arrayContaining(null, null), new Object[]{null, null});
    }
}
