/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsEqual.eq;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;


public class IsEqualTest extends AbstractMatcherTest {

    protected Matcher<?> createMatcher() {
        return eq("irrelevant");
    }

    public void testComparesObjectsUsingEqualsMethod() {
        assertThat("hi", eq("hi"));
        assertThat("bye", not(eq("hi")));

        assertThat(1, eq(1));
        assertThat(1, not(eq(2)));
    }

    public void testCanCompareNullValues() {
        assertThat(null, eq(null));

        assertThat(null, not(eq("hi")));
        assertThat("hi", not(eq(null)));
    }

    public void testComparesTheElementsOfAnObjectArray() {
        String[] s1 = {"a", "b"};
        String[] s2 = {"a", "b"};
        String[] s3 = {"c", "d"};
        String[] s4 = {"a", "b", "c", "d"};

        assertThat(s1, eq(s1));
        assertThat(s2, eq(s1));
        assertThat(s3, not(eq(s1)));
        assertThat(s4, not(eq(s1)));
    }

    public void testComparesTheElementsOfAnArrayOfPrimitiveTypes() {
        int[] i1 = new int[]{1, 2};
        int[] i2 = new int[]{1, 2};
        int[] i3 = new int[]{3, 4};
        int[] i4 = new int[]{1, 2, 3, 4};

        assertThat(i1, eq(i1));
        assertThat(i2, eq(i1));
        assertThat(i3, not(eq(i1)));
        assertThat(i4, not(eq(i1)));
    }

    public void testRecursivelyTestsElementsOfArrays() {
        int[][] i1 = new int[][]{{1, 2}, {3, 4}};
        int[][] i2 = new int[][]{{1, 2}, {3, 4}};
        int[][] i3 = new int[][]{{5, 6}, {7, 8}};
        int[][] i4 = new int[][]{{1, 2, 3, 4}, {3, 4}};

        assertThat(i1, eq(i1));
        assertThat(i2, eq(i1));
        assertThat(i3, not(eq(i1)));
        assertThat(i4, not(eq(i1)));
    }

    public void testIncludesTheResultOfCallingToStringOnItsArgumentInTheDescription() {
        final String argumentDescription = "ARGUMENT DESCRIPTION";
        Object argument = new Object() {
            public String toString() {
                return argumentDescription;
            }
        };
        assertDescription("eq(<" + argumentDescription + ">)", eq(argument));
    }

    public void testReturnsAnObviousDescriptionIfCreatedWithANestedMatcherByMistake() {
        Matcher<String> innerMatcher = eq("NestedMatcher");
        assertDescription("eq(<" + innerMatcher.toString() + ">)", eq(innerMatcher));
    }

    public void testReturnsGoodDescriptionIfCreatedWithNullReference() {
        assertDescription("eq(null)", eq(null));
    }
}

