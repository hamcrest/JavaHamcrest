package org.hamcrest.text;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.text.IsBlankString.blankOrNullString;
import static org.hamcrest.text.IsBlankString.blankString;

public final class IsBlankStringTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = blankString();
        
        assertNullSafe(matcher);
    }

    @Test public void
    matchesEmptyString() {
        assertMatches(blankOrNullString(), "");
        assertMatches(blankString(), "");
    }

    @Test public void
    matchesNullAppropriately() {
        assertMatches(blankOrNullString(), null);
        assertDoesNotMatch(blankString(), null);
    }

    @Test public void
    matchesBlankStringAppropriately() {
        assertMatches(blankString(), " \t");
        assertMatches(blankOrNullString(), " \t");
    }

    @Test public void
    doesNotMatchFilledString() {
        assertDoesNotMatch(blankString(), "a");
        assertDoesNotMatch(blankOrNullString(), "a");
    }

    @Test public void
    describesItself() {
        assertDescription("a blank string", blankString());
        assertDescription("(null or a blank string)", blankOrNullString());
    }

    @Test public void
    describesAMismatch() {
        assertMismatchDescription("was \"a\"", blankString(), "a");
        assertMismatchDescription("was \"a\"", blankOrNullString(), "a");
    }
}
