/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;


public class StringEndsWithTest extends AbstractMatcherTest {
    static final String EXCERPT = "EXCERPT";

    StringEndsWith stringEndsWith;

    public void setUp() {
        stringEndsWith = new StringEndsWith(EXCERPT);
    }

    public void testEvaluatesToTrueIfArgumentContainsSpecifiedSubstring() {
        assertFalse("should be false if excerpt at beginning",
                stringEndsWith.match(EXCERPT + "END"));
        assertTrue("should be true if excerpt at end",
                stringEndsWith.match("START" + EXCERPT));
        assertFalse("should be false if excerpt in middle",
                stringEndsWith.match("START" + EXCERPT + "END"));
        assertTrue("should be true if excerpt is at end and repeated",
                stringEndsWith.match(EXCERPT + EXCERPT));

        assertFalse("should be false if excerpt is not in string",
                stringEndsWith.match("Something else"));
        assertFalse("should be false if part of excerpt is at end of string",
                stringEndsWith.match(EXCERPT.substring(0, EXCERPT.length() - 2)));
    }

    public void testEvaluatesToTrueIfArgumentIsEqualToSubstring() {
        assertTrue("should be true if excerpt is entire string",
                stringEndsWith.match(EXCERPT));
    }
}
