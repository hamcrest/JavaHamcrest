/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest.number;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.number.IsNaN.notANumber;

public final class IsNanTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<Double> matcher = notANumber();
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    matchesNaN() {
        assertMatches(notANumber(), Double.NaN);
    }

    @Test public void
    doesNotMatchDoubleValue() {
        assertDoesNotMatch(notANumber(), 1.25);
    }

    @Test public void
    doesNotMatchInfinity() {
        assertDoesNotMatch(notANumber(), Double.POSITIVE_INFINITY);
    }

    @Test public void
    describesItself() {
        assertDescription("a double value of NaN", notANumber());
    }

    @Test public void
    describesAMismatch() {
        assertMismatchDescription("was <1.25>", notANumber(), 1.25);
    }
}
