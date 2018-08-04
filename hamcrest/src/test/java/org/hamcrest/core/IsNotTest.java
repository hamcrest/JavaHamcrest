/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;

public final class IsNotTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = not("something");

        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    evaluatesToTheTheLogicalNegationOfAnotherMatcher() {
        final Matcher<String> matcher = not(equalTo("A"));

        assertMatches(matcher, "B");
        assertDoesNotMatch(matcher, "A");
    }

    @Test public void
    providesConvenientShortcutForNotEqualTo() {
        final Matcher<String> matcher = not("A");

        assertMatches(matcher, "B");
        assertDoesNotMatch(matcher, "A");
    }

    @Test public void
    usesDescriptionOfNegatedMatcherWithPrefix() {
        assertDescription("not an instance of java.lang.String", not(instanceOf(String.class)));
        assertDescription("not \"A\"", not("A"));
    }
}
