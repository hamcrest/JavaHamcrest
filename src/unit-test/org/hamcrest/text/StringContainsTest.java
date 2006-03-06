/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;


public class StringContainsTest extends AbstractMatcherTest {
    static final String EXCERPT = "EXCERPT";

    StringContains stringContains;

    public void setUp() {
        stringContains = new StringContains(EXCERPT);
    }

    public void testEvaluatesToTrueIfArgumentContainsSpecifiedSubstring() {
        assertTrue("should be true if excerpt at beginning",
                stringContains.match(EXCERPT + "END"));
        assertTrue("should be true if excerpt at end",
                stringContains.match("START" + EXCERPT));
        assertTrue("should be true if excerpt in middle",
                stringContains.match("START" + EXCERPT + "END"));
        assertTrue("should be true if excerpt is repeated",
                stringContains.match(EXCERPT + EXCERPT));

        assertFalse("should not be true if excerpt is not in string",
                stringContains.match("Something else"));
        assertFalse("should not be true if part of excerpt is in string",
                stringContains.match(EXCERPT.substring(1)));
    }

    public void testEvaluatesToTrueIfArgumentIsEqualToSubstring() {
        assertTrue("should be true if excerpt is entire string",
                stringContains.match(EXCERPT));
    }
}
