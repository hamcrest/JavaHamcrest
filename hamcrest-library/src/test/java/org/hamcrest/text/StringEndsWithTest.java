/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import static org.hamcrest.core.StringEndsWith.endsWith;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;


public class StringEndsWithTest extends AbstractMatcherTest {
    static final String EXCERPT = "EXCERPT";
    Matcher<String> stringEndsWith = endsWith(EXCERPT);

    @Override
    protected Matcher<?> createMatcher() {
        return stringEndsWith;
    }

    public void testEvaluatesToTrueIfArgumentContainsSpecifiedSubstring() {
        assertFalse("should be false if excerpt at beginning",
                stringEndsWith.matches(EXCERPT + "END"));
        assertTrue("should be true if excerpt at end",
                stringEndsWith.matches("START" + EXCERPT));
        assertFalse("should be false if excerpt in middle",
                stringEndsWith.matches("START" + EXCERPT + "END"));
        assertTrue("should be true if excerpt is at end and repeated",
                stringEndsWith.matches(EXCERPT + EXCERPT));

        assertFalse("should be false if excerpt is not in string",
                stringEndsWith.matches("Something else"));
        assertFalse("should be false if part of excerpt is at end of string",
                stringEndsWith.matches(EXCERPT.substring(0, EXCERPT.length() - 2)));
    }

    public void testEvaluatesToTrueIfArgumentIsEqualToSubstring() {
        assertTrue("should be true if excerpt is entire string",
                stringEndsWith.matches(EXCERPT));
    }

    public void testHasAReadableDescription() {
        assertDescription("a string ending with \"EXCERPT\"", stringEndsWith);
    }
}
