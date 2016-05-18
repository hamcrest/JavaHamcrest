package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringContains.containsStringIgnoringCase;


public class StringContainsTest extends AbstractMatcherTest {
    static final String EXCERPT = "EXCERPT";
    final Matcher<String> stringContains = containsString(EXCERPT);

    @Override
    protected Matcher<?> createMatcher() {
        return stringContains;
    }

    public void testMatchesSubstrings() {
        assertMatches(stringContains, EXCERPT + "END");
        assertMatches(stringContains, "START" + EXCERPT);
        assertMatches(stringContains, "START" + EXCERPT + "END");
        assertMatches(stringContains, EXCERPT);
        assertDoesNotMatch(stringContains, EXCERPT.toLowerCase());
        assertMatches(stringContains, EXCERPT + EXCERPT);
        assertDoesNotMatch(stringContains, "XC");

        assertMismatchDescription("was \"Something else\"", stringContains, "Something else");
        assertDescription("a string containing \"EXCERPT\"", stringContains);
    }

    public void testMatchesSubstringsIgnoringCase() {
        final Matcher<String> ignoringCase = containsStringIgnoringCase("ExCert");
        assertMatches(ignoringCase, "eXcERT" + "END");
        assertMatches(ignoringCase, "START" + "EXCert");
        assertMatches(ignoringCase, "START" + "excERT" + "END");
        assertMatches(ignoringCase, "eXCert" + "excErt");
        assertDoesNotMatch(ignoringCase, "xc");

        assertMismatchDescription("was \"Something else\"", ignoringCase, "Something else");
        assertDescription("a string containing \"ExCert\" ignoring case", ignoringCase);
    }


}
