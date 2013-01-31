/*  Copyright (c) 2000-2010 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;

import org.hamcrest.Matcher;
import org.junit.Test;

public final class IsInstanceOfTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<?> matcher = instanceOf(Number.class);

        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    evaluatesToTrueIfArgumentIsInstanceOfASpecificClass() {
        final Matcher<Object> matcher = instanceOf(Number.class);

        assertMatches("didn't match", matcher, 1);
        assertMatches("didn't match", matcher, 1.1);
        assertDoesNotMatch("matched unexpectedly", matcher, null);
        assertDoesNotMatch("matched unexpectedly", matcher, new Object());
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("an instance of java.lang.Number", instanceOf(Number.class));
    }

    @Test public void
    describesActualClassInMismatchMessage() {
        assertMismatchDescription("\"some text\" was a java.lang.String", instanceOf(Number.class), "some text");
    }

    @Test public void
    hasCorrectParameterType() {
        assertEquals(Number.class, any(Number.class).getParameterType());
        assertEquals(int.class, any(int.class).getParameterType());
        assertEquals(Integer.class, any(Integer.class).getParameterType());
    }

    @Test public void
    matchesPrimitiveTypes() {
        assertMatches("didn't match", any(boolean.class), true);
        assertMatches("didn't match", any(byte.class), (byte)1);
        assertMatches("didn't match", any(char.class), 'x');
        assertMatches("didn't match", any(double.class), 5.0);
        assertMatches("didn't match", any(float.class), 5.0f);
        assertMatches("didn't match", any(int.class), 2);
        assertMatches("didn't match", any(long.class), 4L);
        assertMatches("didn't match", any(short.class), (short)1);
    }

    // The following two tests are effectively compile-time tests
    // rather than run-time tests and primarily serve as a reminder
    // to future maintainers about the intended difference between
    // instanceOf() and any().

    @Test public void
    returnTypeOfInstanceOfIsIndependentOfArgumentType() {
        @SuppressWarnings("unused")
        Matcher<String> matcher = instanceOf(Integer.class);
    }

    @Test public void
    returnTypeOfAnyMatchesArgumentType() {
        @SuppressWarnings("unused")
        Matcher<Integer> matcher = any(Integer.class);
    }
}

