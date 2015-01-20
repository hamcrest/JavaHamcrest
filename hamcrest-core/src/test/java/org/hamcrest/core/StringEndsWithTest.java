package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringEndsWith.endsWithIgnoringCase;


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
        assertDoesNotMatch(stringEndsWith, EXCERPT.toLowerCase());
        assertDoesNotMatch(stringEndsWith, "START" + EXCERPT + "END");
        assertMatches(stringEndsWith, EXCERPT + EXCERPT);
        assertDoesNotMatch(stringEndsWith, "EXCER");

        assertMismatchDescription("was \"Something else\"", stringEndsWith, "Something else");
        assertDescription("a string ending with \"EXCERPT\"", stringEndsWith);
    }

    public void testMatchesSubstringAtEndIngoringCase() {
        final Matcher<String> ignoringCase = endsWithIgnoringCase("EXCERpt");
        assertDoesNotMatch(ignoringCase, "eXCErpt" + "END");
        assertMatches(ignoringCase, "START" + "EXceRpt");
        assertMatches(ignoringCase, "EXcerPT");
        assertDoesNotMatch(ignoringCase, "START" + "ExcERpt" + "END");
        assertMatches(ignoringCase, "exCERpt" + "EXCerPt");
        assertDoesNotMatch(ignoringCase, "ExcER");

        assertMismatchDescription("was \"Something else\"", ignoringCase, "Something else");
        assertDescription("a string ending with \"EXCERpt\" ignoring case", ignoringCase);
    }

}
