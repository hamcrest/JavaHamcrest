package org.hamcrest.text;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.hamcrest.text.IsEmptyString.emptyString;

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
    matchesNullAppropriately() {
        assertMatches("didn't match", emptyOrNullString(), null);
        assertDoesNotMatch("matched unexpectedly", emptyString(), null);
    }

    @Test public void
    matchesEmptyString() {
        assertMatches("didn't match", emptyOrNullString(), "");
        assertMatches("didn't match", emptyString(), "");
    }

    @Test public void
    doesNotmatchFilledString() {
        assertDoesNotMatch("matched unexpectedly", emptyOrNullString(), "a");
        assertDoesNotMatch("matched unexpectedly", emptyString(), "a");
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
