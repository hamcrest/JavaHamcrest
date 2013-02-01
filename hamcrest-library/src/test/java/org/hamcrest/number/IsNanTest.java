package org.hamcrest.number;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.number.IsNaN.notANumber;

import org.hamcrest.Matcher;
import org.junit.Test;

public final class IsNanTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<Double> matcher = notANumber();
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    matchesNaN() {
        assertMatches("didn't match", notANumber(), Double.NaN);
    }

    @Test public void
    doesNotMatchDoubleValue() {
        assertDoesNotMatch("matched unexpectedly", notANumber(), 1.25);
    }

    @Test public void
    doesNotMatchInfinity() {
        assertDoesNotMatch("matched unexpectedly", notANumber(), Double.POSITIVE_INFINITY);
    }

    @Test public void
    describesItself() {
        assertDescription("NaN", notANumber());
    }

    @Test public void
    describesAMismatch() {
        assertMismatchDescription("was <1.25>", notANumber(), 1.25);
    }
}
