package org.hamcrest.text;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.hamcrest.text.IsEmptyString.emptyString;

public final class IsEmptyStringTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = emptyString();
        
        assertNullSafe(matcher);
    }

    @Test public void
    matchesEmptyString() {
        assertMatches(emptyOrNullString(), "");
        assertMatches(emptyString(), "");
    }

    @Test public void
    matchesNullAppropriately() {
        assertMatches(emptyOrNullString(), null);
        assertDoesNotMatch(emptyString(), null);
    }

    @Test public void
    matchesBlankStringAppropriately() {
        assertDoesNotMatch(emptyString(), "  ");
        assertDoesNotMatch(emptyOrNullString(), "  ");
    }

    @Test public void
    doesNotMatchFilledString() {
        assertDoesNotMatch(emptyString(), "a");
        assertDoesNotMatch(emptyOrNullString(), "a");
    }

    @Test public void
    describesItself() {
        assertDescription("an empty string", emptyString());
        assertDescription("(null or an empty string)", emptyOrNullString());
    }

    @Test public void
    describesAMismatch() {
        assertMismatchDescription("was \"a\"", emptyString(), "a");
        assertMismatchDescription("was \"a\"", emptyOrNullString(), "a");
    }
}
