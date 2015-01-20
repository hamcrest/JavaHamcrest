package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.hamcrest.core.StringStartsWith.startsWithIgnoringCase;


public class StringStartsWithTest extends AbstractMatcherTest {
    static final String EXCERPT = "EXCERPT";
    final Matcher<String> stringStartsWith = startsWith(EXCERPT);

    @Override
    protected Matcher<?> createMatcher() {
        return stringStartsWith;
    }

    public void testMatchesStringAtStart() {
        assertMatches(stringStartsWith, EXCERPT + "END");
        assertDoesNotMatch(stringStartsWith, "START" + EXCERPT);
        assertDoesNotMatch(stringStartsWith, "START" + EXCERPT + "END");
        assertMatches(stringStartsWith, EXCERPT);
        assertDoesNotMatch(stringStartsWith, EXCERPT.toLowerCase());
        assertMatches(stringStartsWith, EXCERPT + EXCERPT);
        assertDoesNotMatch(stringStartsWith, "EXCER");

        assertDescription("a string starting with \"EXCERPT\"", stringStartsWith);
        assertMismatchDescription("was \"Something else\"", stringStartsWith, "Something else");
    }

    public void testMatchesStringAtStartIgnoringCase() {
        final Matcher<String> ignoreCase = startsWithIgnoringCase("EXCerPT");

        assertMatches(ignoreCase, "exCerPT" + "END");
        assertDoesNotMatch(ignoreCase, "START" + "EXCerpt");
        assertDoesNotMatch(ignoreCase, "START" + "EXcerpT" + "END");
        assertMatches(ignoreCase, "excERPT");
        assertMatches(ignoreCase, "ExcerPT" + "EXCerpt");
        assertDoesNotMatch(ignoreCase, "ExcER");

        assertDescription("a string starting with \"EXCerPT\" ignoring case", ignoreCase);
        assertMismatchDescription("was \"Something else\"", ignoreCase, "Something else");
    }

}
