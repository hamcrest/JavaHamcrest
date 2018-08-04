/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
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
        assertUnknownTypeSafe(matcher);
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
