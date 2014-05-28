/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

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

    public void testMatchesStringAtStart() {
        assertMatches(stringStartsWith, EXCERPT + "END");
        assertDoesNotMatch(stringStartsWith, "START" + EXCERPT);
        assertDoesNotMatch(stringStartsWith, "START" + EXCERPT + "END");
        assertMatches(stringStartsWith, EXCERPT);
        assertMatches(stringStartsWith, EXCERPT + EXCERPT);
        assertDoesNotMatch(stringStartsWith, "EXCER");

        assertMismatchDescription("was \"Something else\"", stringStartsWith, "Something else");
    }

    public void testHasAReadableDescription() {
        assertDescription("a string starting with \"EXCERPT\"", stringStartsWith);
    }
}
