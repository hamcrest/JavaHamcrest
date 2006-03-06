/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;


public class IsEqualTest extends AbstractMatcherTest {
    public void testComparesObjectsUsingEqualsMethod() {
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(2);
        Matcher c = new IsEqual(i1);

        assertTrue(c.match(i1));
        assertTrue(c.match(new Integer(1)));
        assertTrue(!c.match(i2));
    }

    public void testCanCompareNullValues() {
        Integer i1 = new Integer(1);
        Matcher c = new IsEqual(i1);

        assertTrue(!c.match(null));
        Matcher nullEquals = new IsEqual(null);
        assertTrue(nullEquals.match(null));
        assertTrue(!nullEquals.match(i1));
    }

    public void testComparesTheElementsOfAnObjectArray() {
        String[] s1 = new String[]{"a", "b"};
        String[] s2 = new String[]{"a", "b"};
        String[] s3 = new String[]{"c", "d"};
        String[] s4 = new String[]{"a", "b", "c", "d"};

        Matcher c = new IsEqual(s1);

        assertTrue("Should equal itself", c.match(s1));
        assertTrue("Should equal a similar array", c.match(s2));
        assertTrue("Should not equal a different array", !c.match(s3));
        assertTrue("Should not equal a different sized array", !c.match(s4));
    }

    public void testComparesTheElementsOfAnArrayOfPrimitiveTypes() {
        int[] i1 = new int[]{1, 2};
        int[] i2 = new int[]{1, 2};
        int[] i3 = new int[]{3, 4};
        int[] i4 = new int[]{1, 2, 3, 4};

        Matcher c = new IsEqual(i1);

        assertTrue("Should equal itself", c.match(i1));
        assertTrue("Should equal a similar array", c.match(i2));
        assertTrue("Should not equal a different array", !c.match(i3));
        assertTrue("Should not equal a different sized array", !c.match(i4));
    }

    public void testRecursivelyTestsElementsOfArrays() {
        int[][] i1 = new int[][]{{1, 2}, {3, 4}};
        int[][] i2 = new int[][]{{1, 2}, {3, 4}};
        int[][] i3 = new int[][]{{5, 6}, {7, 8}};
        int[] i4 = new int[]{1, 2, 3, 4};
        int[][] i5 = new int[][]{{1, 2, 3, 4}, {3, 4}};

        Matcher c = new IsEqual(i1);

        assertTrue("Should equal itself", c.match(i1));
        assertTrue("Should equal a similar array", c.match(i2));
        assertTrue("Should not equal a different array", !c.match(i3));
        assertTrue("Should not equal a different sized array", !c.match(i4));
        assertTrue("Should not equal a different sized subarray", !c.match(i5));
    }

    public void testIncludesTheResultOfCallingToStringOnItsArgumentInTheDescription() {
        final String argumentDescription = "ARGUMENT DESCRIPTION";
        Object argument = new Object() {
            public String toString() {
                return argumentDescription;
            }
        };
        Matcher c = new IsEqual(argument);

        assertDescription("eq(<" + argumentDescription + ">)", c);
    }

    public void testReturnsAnObviousDescriptionIfCreatedWithANestedMatcherByMistake() {
        IsEqual innerMatcher = new IsEqual("NestedMatcher");
        assertDescription("eq(<" + innerMatcher.toString() + ">)", new IsEqual(innerMatcher));
    }

    public void testReturnsGoodDescriptionIfCreatedWithNullReference() {
        assertDescription("eq(null)", new IsEqual(null));
    }
}

