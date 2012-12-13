/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import static org.hamcrest.core.StringContains.containsString;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;


public class StringContainsTest extends AbstractMatcherTest {
    static final String EXCERPT = "EXCERPT";
    Matcher<String> stringContains = containsString(EXCERPT);

    @Override
    protected Matcher<?> createMatcher() {
        return stringContains;
    }

    public void testEvaluatesToTrueIfArgumentContainsSpecifiedSubstring() {
        assertTrue("should be true if excerpt at beginning",
                stringContains.matches(EXCERPT + "END"));
        assertTrue("should be true if excerpt at end",
                stringContains.matches("START" + EXCERPT));
        assertTrue("should be true if excerpt in middle",
                stringContains.matches("START" + EXCERPT + "END"));
        assertTrue("should be true if excerpt is repeated",
                stringContains.matches(EXCERPT + EXCERPT));

        assertFalse("should not be true if excerpt is not in string",
                stringContains.matches("Something else"));
        assertFalse("should not be true if part of excerpt is in string",
                stringContains.matches(EXCERPT.substring(1)));
    }

    public void testEvaluatesToTrueIfArgumentIsEqualToSubstring() {
        assertTrue("should be true if excerpt is entire string",
                stringContains.matches(EXCERPT));
    }

    public void testHasAReadableDescription() {
        assertDescription("a string containing \"EXCERPT\"", stringContains);
    }
}
