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
        assertDoesNotMatch(stringEndsWith, EXCERPT + "END");
        assertMatches(stringEndsWith, "START" + EXCERPT);
        assertMatches(stringEndsWith, EXCERPT);
        assertDoesNotMatch(stringEndsWith, "START" + EXCERPT + "END");
        assertMatches(stringEndsWith, EXCERPT + EXCERPT);
        assertDoesNotMatch(stringEndsWith, "EXCER");

        assertMismatchDescription("was \"Something else\"", stringEndsWith, "Something else");
        assertDescription("a string ending with \"EXCERPT\"", stringEndsWith);
    }

    public void testMatchesSubstringAtEndIngoringCase() {
        final Matcher<String> ingoringCase = endsWith("EXCERpt");
        assertDoesNotMatch(ingoringCase, "eXCErpt" + "END");
        assertMatches(ingoringCase, "START" + "EXceRpt");
        assertMatches(ingoringCase, "EXcerPT");
        assertDoesNotMatch(ingoringCase, "START" + "ExcERpt" + "END");
        assertMatches(ingoringCase, "exCERpt" + "EXCerPt");
        assertDoesNotMatch(ingoringCase, "ExcER");

        assertMismatchDescription("was \"Something else\"", ingoringCase, "Something else");
        assertDescription("a string ending with \"EXCERpt\" ignoring case", ingoringCase);
    }

}
