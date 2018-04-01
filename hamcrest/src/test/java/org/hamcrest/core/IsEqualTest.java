package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import org.hamcrest.Matchers;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsEqual.equalToObject;

public final class IsEqualTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<?> matcher = equalTo("irrelevant");

        assertNullSafe(matcher);
    }

    @Test public void
    comparesObjectsUsingEqualsMethod() {
        final Matcher<String> matcher1 = equalTo("hi");
        assertMatches(matcher1, "hi");
        assertDoesNotMatch(matcher1, "bye");
        assertDoesNotMatch(matcher1, null);

        final Matcher<Integer> matcher2 = equalTo(1);
        assertMatches(matcher2, 1);
        assertDoesNotMatch(matcher2, 2);
        assertDoesNotMatch(matcher2, null);
    }

    @Test public void
    canCompareNullValues() {
        final Matcher<Object> matcher = equalTo(null);
        
        assertMatches(matcher, null);
        assertDoesNotMatch(matcher, 2);
        assertDoesNotMatch(matcher, "hi");
        assertDoesNotMatch(matcher, new String[] {"a", "b"});
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
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

        Matcher<Object> matcher = equalTo(null);

        assertMatches(matcher, alwaysEqual);
        assertDoesNotMatch(matcher, neverEqual);
    }

    @Test public void
    comparesTheElementsOfAnObjectArray() {
        String[] s1 = {"a", "b"};
        String[] s2 = {"a", "b"};
        String[] s3 = {"c", "d"};
        String[] s4 = {"a", "b", "c", "d"};

        final Matcher<String[]> matcher = equalTo(s1);
        assertMatches(matcher, s1);
        assertMatches(matcher, s2);
        assertDoesNotMatch(matcher, s3);
        assertDoesNotMatch(matcher, s4);
        assertDoesNotMatch(matcher, null);
    }

    @Test public void
    comparesTheElementsOfArraysWithNulls() {
        String[] s1 = {"a", null, "b"};
        String[] s2 = {"a", null, "b"};
        String[] s3 = {"c", "d"};
        String[] s4 = {"a", "b", "c", "d"};

        final Matcher<String[]> matcher = equalTo(s1);
        assertMatches(matcher, s1);
        assertMatches(matcher, s2);
        assertDoesNotMatch(matcher, s3);
        assertDoesNotMatch(matcher, s4);
    }

    @Test public void
    comparesTheElementsOfAnArrayOfPrimitiveTypes() {
        int[] i1 = new int[]{1, 2};
        int[] i2 = new int[]{1, 2};
        int[] i3 = new int[]{3, 4};
        int[] i4 = new int[]{1, 2, 3, 4};

        final Matcher<int[]> matcher = equalTo(i1);
        assertMatches(matcher, i1);
        assertMatches(matcher, i2);
        assertDoesNotMatch(matcher, i3);
        assertDoesNotMatch(matcher, i4);
        assertDoesNotMatch(matcher, null);
    }

    @Test public void
    recursivelyTestsElementsOfArrays() {
        int[][] i1 = new int[][]{{1, 2}, {3, 4}};
        int[][] i2 = new int[][]{{1, 2}, {3, 4}};
        int[][] i3 = new int[][]{{5, 6}, {7, 8}};
        int[][] i4 = new int[][]{{1, 2, 3, 4}, {3, 4}};

        final Matcher<int[][]> matcher = equalTo(i1);
        assertMatches(matcher, i1);
        assertMatches(matcher, i2);
        assertDoesNotMatch(matcher, i3);
        assertDoesNotMatch(matcher, i4);
        assertDoesNotMatch(matcher, null);
    }

    @Test public void
    hasUntypedVariant() {
        Object original = 10;

        assertMatches(Matchers.<Object>equalToObject(10), original);
        assertDoesNotMatch(Matchers.<Object>equalToObject(0), original);
        assertDoesNotMatch(Matchers.<Object>equalToObject("10"), original);
        assertDoesNotMatch(Matchers.<Object>equalToObject(10), "10");
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
}

