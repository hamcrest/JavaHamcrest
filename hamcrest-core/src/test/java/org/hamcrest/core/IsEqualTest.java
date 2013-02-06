/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.hamcrest.Matcher;
import org.junit.Test;

public final class IsEqualTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<?> matcher = equalTo("irrelevant");

        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    comparesObjectsUsingEqualsMethod() {
        final Matcher<String> matcher1 = equalTo("hi");
        assertMatches("didn't match", matcher1, "hi");
        assertDoesNotMatch("matched unexpectedly", matcher1, "bye");
        assertDoesNotMatch("matched unexpectedly", matcher1, null);

        final Matcher<Integer> matcher2 = equalTo(1);
        assertMatches("didn't match", matcher2, 1);
        assertDoesNotMatch("matched unexpectedly", matcher2, 2);
        assertDoesNotMatch("matched unexpectedly", matcher2, null);
    }

    @Test public void
    canCompareNullValues() {
        final Matcher<Object> matcher = equalTo(null);
        
        assertMatches("didn't match", matcher, null);
        assertDoesNotMatch("matched unexpectedly", matcher, 2);
        assertDoesNotMatch("matched unexpectedly", matcher, "hi");
        assertDoesNotMatch("matched unexpectedly", matcher, new String[] {"a", "b"});
    }

    @Test public void
    honoursIsEqualImplementationEvenWithNullValues() {
        Object alwaysEqual = new Object() {
            @Override
            public boolean equals(Object obj) {
                return true;
            }
        };
        Object neverEqual = new Object() {
            @Override
            public boolean equals(Object obj) {
                return false;
            }
        };

        final Matcher<Object> matcher = equalTo(null);
        assertMatches("didn't match", matcher, alwaysEqual);
        assertDoesNotMatch("matched unexpectedly", matcher, neverEqual);
    }

    @Test public void
    comparesTheElementsOfAnObjectArray() {
        String[] s1 = {"a", "b"};
        String[] s2 = {"a", "b"};
        String[] s3 = {"c", "d"};
        String[] s4 = {"a", "b", "c", "d"};

        final Matcher<String[]> matcher = equalTo(s1);
        assertMatches("didn't match", matcher, s1);
        assertMatches("didn't match", matcher, s2);
        assertDoesNotMatch("matched unexpectedly", matcher, s3);
        assertDoesNotMatch("matched unexpectedly", matcher, s4);
        assertDoesNotMatch("matched unexpectedly", matcher, null);
    }

    @Test public void
    comparesTheElementsOfAnArrayOfPrimitiveTypes() {
        int[] i1 = new int[]{1, 2};
        int[] i2 = new int[]{1, 2};
        int[] i3 = new int[]{3, 4};
        int[] i4 = new int[]{1, 2, 3, 4};

        final Matcher<int[]> matcher = equalTo(i1);
        assertMatches("didn't match", matcher, i1);
        assertMatches("didn't match", matcher, i2);
        assertDoesNotMatch("matched unexpectedly", matcher, i3);
        assertDoesNotMatch("matched unexpectedly", matcher, i4);
        assertDoesNotMatch("matched unexpectedly", matcher, null);
    }

    @Test public void
    recursivelyTestsElementsOfArrays() {
        int[][] i1 = new int[][]{{1, 2}, {3, 4}};
        int[][] i2 = new int[][]{{1, 2}, {3, 4}};
        int[][] i3 = new int[][]{{5, 6}, {7, 8}};
        int[][] i4 = new int[][]{{1, 2, 3, 4}, {3, 4}};

        final Matcher<int[][]> matcher = equalTo(i1);
        assertMatches("didn't match", matcher, i1);
        assertMatches("didn't match", matcher, i2);
        assertDoesNotMatch("matched unexpectedly", matcher, i3);
        assertDoesNotMatch("matched unexpectedly", matcher, i4);
        assertDoesNotMatch("matched unexpectedly", matcher, null);
    }

    @Test public void
    includesTheResultOfCallingToStringOnItsArgumentInTheDescription() {
        final String argumentDescription = "ARGUMENT DESCRIPTION";
        Object argument = new Object() {
            @Override
            public String toString() {
                return argumentDescription;
            }
        };
        assertDescription("<" + argumentDescription + ">", equalTo(argument));
    }

    @Test public void
    returnsAnObviousDescriptionIfCreatedWithANestedMatcherByMistake() {
        Matcher<? super String> innerMatcher = equalTo("NestedMatcher");
        assertDescription("<" + innerMatcher.toString() + ">", equalTo(innerMatcher));
    }

    @Test public void
    returnsGoodDescriptionIfCreatedWithNullReference() {
        assertDescription("null", equalTo(null));
    }

    @Test public void
    hasCorrectParameterType() {
        assertEquals(String.class, equalTo("hi").getParameterType());
        assertEquals(Integer.class, equalTo(1).getParameterType());
        assertNull(equalTo(null).getParameterType());
    }
}

