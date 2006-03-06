/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;


public class StringStartsWithTest extends AbstractMatcherTest {
    static final String EXCERPT = "EXCERPT";

    StringStartsWith stringStartsWith;

    public void setUp() {
        stringStartsWith = new StringStartsWith(EXCERPT);
    }

    public void testEvaluatesToTrueIfArgumentContainsSpecifiedSubstring() {
        assertTrue("should be true if excerpt at beginning",
                stringStartsWith.match(EXCERPT + "END"));
        assertFalse("should be false if excerpt at end",
                stringStartsWith.match("START" + EXCERPT));
        assertFalse("should be false if excerpt in middle",
                stringStartsWith.match("START" + EXCERPT + "END"));
        assertTrue("should be true if excerpt is at beginning and repeated",
                stringStartsWith.match(EXCERPT + EXCERPT));

        assertFalse("should be false if excerpt is not in string",
                stringStartsWith.match("Something else"));
        assertFalse("should be false if part of excerpt is at start of string",
                stringStartsWith.match(EXCERPT.substring(1)));
    }

    public void testEvaluatesToTrueIfArgumentIsEqualToSubstring() {
        assertTrue("should be true if excerpt is entire string",
                stringStartsWith.match(EXCERPT));
    }
}
