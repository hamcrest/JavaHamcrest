package org.hamcrest.text;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.hamcrest.text.IsEmptyString.emptyString;
import static org.hamcrest.text.IsEmptyString.blankString;

import org.hamcrest.Matcher;
import org.junit.Test;

public final class IsEmptyStringTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = emptyOrNullString();
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    matchesEmptyString() {
        assertMatches("didn't match", emptyOrNullString(), "");
        assertMatches("didn't match", emptyString(), "");
        assertMatches("didn't match", blankString(), "");
    }

    @Test public void
    matchesNullAppropriately() {
        assertMatches("didn't match", emptyOrNullString(), null);
        assertDoesNotMatch("matched unexpectedly", emptyString(), null);
        assertDoesNotMatch("matched unexpectedly", blankString(), null);
    }

    @Test public void
    matchesBlankStringAppropriately() {
        assertMatches("didn't match", blankString(), "  ");
        assertDoesNotMatch("matched unexpectedly", emptyString(), "  ");
        assertDoesNotMatch("matched unexpectedly", emptyOrNullString(), "  ");
    }

    @Test public void
    doesNotmatchFilledString() {
        assertDoesNotMatch("matched unexpectedly", emptyString(), "a");
        assertDoesNotMatch("matched unexpectedly", blankString(), "a");
        assertDoesNotMatch("matched unexpectedly", emptyOrNullString(), "a");
    }

    @Test public void
    describesItself() {
        assertDescription("an empty string", emptyString());
        assertDescription("a blank string", blankString());
        assertDescription("(null or an empty string)", emptyOrNullString());
    }

    @Test public void
    describesAMismatch() {
        assertMismatchDescription("was \"a\"", emptyString(), "a");
        assertMismatchDescription("was \"a\"", blankString(), "a");
        assertMismatchDescription("was \"a\"", emptyOrNullString(), "a");
    }
}
