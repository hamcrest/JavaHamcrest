/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import static org.hamcrest.core.StringStartsWith.startsWith;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;


public class StringStartsWithTest extends AbstractMatcherTest {
    static final String EXCERPT = "EXCERPT";
    Matcher<String> stringStartsWith = startsWith(EXCERPT);

    @Override
    protected Matcher<?> createMatcher() {
        return stringStartsWith;
    }

    public void testEvaluatesToTrueIfArgumentContainsSpecifiedSubstring() {
        assertTrue("should be true if excerpt at beginning",
                stringStartsWith.matches(EXCERPT + "END"));
        assertFalse("should be false if excerpt at end",
                stringStartsWith.matches("START" + EXCERPT));
        assertFalse("should be false if excerpt in middle",
                stringStartsWith.matches("START" + EXCERPT + "END"));
        assertTrue("should be true if excerpt is at beginning and repeated",
                stringStartsWith.matches(EXCERPT + EXCERPT));

        assertFalse("should be false if excerpt is not in string",
                stringStartsWith.matches("Something else"));
        assertFalse("should be false if part of excerpt is at start of string",
                stringStartsWith.matches(EXCERPT.substring(1)));
    }

    public void testEvaluatesToTrueIfArgumentIsEqualToSubstring() {
        assertTrue("should be true if excerpt is entire string",
                stringStartsWith.matches(EXCERPT));
    }

    public void testHasAReadableDescription() {
        assertDescription("a string starting with \"EXCERPT\"", stringStartsWith);
    }
}
