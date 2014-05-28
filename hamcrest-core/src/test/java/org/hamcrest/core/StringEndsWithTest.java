/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.core.StringEndsWith.endsWith;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;


public class StringEndsWithTest extends AbstractMatcherTest {
    static final String EXCERPT = "EXCERPT";
    final Matcher<String> stringEndsWith = endsWith(EXCERPT);

    @Override
    protected Matcher<?> createMatcher() {
        return stringEndsWith;
    }

    public void testMatchesSubstringAtEnd() {
        assertMatches(stringEndsWith, EXCERPT + "END");
        assertMatches(stringEndsWith, "START" + EXCERPT);
        assertDoesNotMatch(stringEndsWith, "START" + EXCERPT + "END");
        assertMatches(stringEndsWith, EXCERPT + EXCERPT);

        assertDoesNotMatch(stringEndsWith, "EXCER");
        assertMismatchDescription("bad", stringEndsWith, "Something else");
    }

    public void testEvaluatesToTrueIfArgumentIsEqualToSubstring() {
        assertTrue("should be true if excerpt is entire string",
                stringEndsWith.matches(EXCERPT));
    }

    public void testHasAReadableDescription() {
        assertDescription("a string ending with \"EXCERPT\"", stringEndsWith);
    }
}
