/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsEqualIgnoringCaseTest extends AbstractMatcherTest {

    private final Matcher matcher = new IsEqualIgnoringCase("heLLo");

    public void testIgnoresCaseOfCharsInString() {
        assertTrue(matcher.match("HELLO"));
        assertTrue(matcher.match("hello"));
        assertTrue(matcher.match("HelLo"));

        assertFalse(matcher.match("abcde"));
    }

    public void testFailsIfAdditionalWhitespaceIsPresent() {
        assertFalse(matcher.match(" heLLo"));
        assertFalse(matcher.match("heLLo "));
    }

    public void testFailsIfNotMatchingAgainstString() {
        assertFalse(matcher.match(new Object()));
    }

    public void testFailsIfMatchingAgainstNull() {
        assertFalse(matcher.match(null));
    }

    public void testRequiresNonNullStringToBeConstructed() {
        try {
            new IsEqualIgnoringCase(null);
            fail("Expected exception");
        } catch (IllegalArgumentException goodException) {
            // expected!
        }
    }

    public void testDescribesItselfAsCaseInsensitive() {
        assertDescription("eqIgnoringCase(\"heLLo\")", matcher);
    }
}
