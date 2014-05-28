/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.core.StringContains.containsString;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.Test;


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
        assertMatches(stringContains, EXCERPT + EXCERPT);
        assertMismatchDescription("was \"Something else\"", stringContains, "Something else");
        assertMismatchDescription("was \"XC\"", stringContains, "XC");
    }


    public void testHasAReadableDescription() {
        assertDescription("a string containing \"EXCERPT\"", stringContains);
    }
}
