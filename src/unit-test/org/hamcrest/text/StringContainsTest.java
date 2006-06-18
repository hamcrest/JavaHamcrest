/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import static org.hamcrest.text.StringContains.stringContains;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;


public class StringContainsTest extends AbstractMatcherTest {
    static final String EXCERPT = "EXCERPT";

    Matcher<String> stringContains = stringContains(EXCERPT);

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
