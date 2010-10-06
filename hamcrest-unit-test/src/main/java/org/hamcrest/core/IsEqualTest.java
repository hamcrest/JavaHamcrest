/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;


public class IsEqualTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return equalTo("irrelevant");
    }

    public void testComparesObjectsUsingEqualsMethod() {
        assertThat("hi", equalTo("hi"));
        assertThat("bye", not(equalTo("hi")));

        assertThat(1, equalTo(1));
        assertThat(1, not(equalTo(2)));
    }

    public void testCanCompareNullValues() {
        assertThat(null, equalTo(null));

        assertThat(null, not(equalTo("hi")));
        assertThat("hi", not(equalTo(null)));
    }

    public void testHonoursIsEqualImplementationEvenWithNullValues() {
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

        assertThat(alwaysEqual, equalTo(null));
        assertThat(neverEqual, not(equalTo(null)));
    }
    
    public void testComparesTheElementsOfAnObjectArray() {
        String[] s1 = {"a", "b"};
        String[] s2 = {"a", "b"};
        String[] s3 = {"c", "d"};
        String[] s4 = {"a", "b", "c", "d"};

        assertThat(s1, equalTo(s1));
        assertThat(s2, equalTo(s1));
        assertThat(s3, not(equalTo(s1)));
        assertThat(s4, not(equalTo(s1)));
    }

    public void testComparesArraysToNull() {
        assertThat(new String[]{"a", "b"}, not(equalTo(null)));
        assertThat(null, not(equalTo(new String[]{"a", "b"})));
    }

    public void testComparesTheElementsOfAnArrayOfPrimitiveTypes() {
        int[] i1 = new int[]{1, 2};
        int[] i2 = new int[]{1, 2};
        int[] i3 = new int[]{3, 4};
        int[] i4 = new int[]{1, 2, 3, 4};

        assertThat(i1, equalTo(i1));
        assertThat(i2, equalTo(i1));
        assertThat(i3, not(equalTo(i1)));
        assertThat(i4, not(equalTo(i1)));
    }

    public void testRecursivelyTestsElementsOfArrays() {
        int[][] i1 = new int[][]{{1, 2}, {3, 4}};
        int[][] i2 = new int[][]{{1, 2}, {3, 4}};
        int[][] i3 = new int[][]{{5, 6}, {7, 8}};
        int[][] i4 = new int[][]{{1, 2, 3, 4}, {3, 4}};

        assertThat(i1, equalTo(i1));
        assertThat(i2, equalTo(i1));
        assertThat(i3, not(equalTo(i1)));
        assertThat(i4, not(equalTo(i1)));
    }

    public void testIncludesTheResultOfCallingToStringOnItsArgumentInTheDescription() {
        final String argumentDescription = "ARGUMENT DESCRIPTION";
        Object argument = new Object() {
            @Override
            public String toString() {
                return argumentDescription;
            }
        };
        assertDescription("<" + argumentDescription + ">", equalTo(argument));
    }

    public void testReturnsAnObviousDescriptionIfCreatedWithANestedMatcherByMistake() {
        Matcher<? super String> innerMatcher = equalTo("NestedMatcher");
        assertDescription("<" + innerMatcher.toString() + ">", equalTo(innerMatcher));
    }

    public void testReturnsGoodDescriptionIfCreatedWithNullReference() {
        assertDescription("null", equalTo(null));
    }
}

