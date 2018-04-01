package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public final class IsInstanceOfTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<?> matcher = instanceOf(Number.class);

        assertNullSafe(matcher);
    }

    @Test public void
    evaluatesToTrueIfArgumentIsInstanceOfASpecificClass() {
        final Matcher<Object> matcher = instanceOf(Number.class);

        assertMatches(matcher, 1);
        assertMatches(matcher, 1.1);
        assertDoesNotMatch(matcher, null);
        assertDoesNotMatch(matcher, new Object());
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("an instance of java.lang.Number", instanceOf(Number.class));
    }

    @Test public void
    describesActualClassInMismatchMessage() {
        assertMismatchDescription("\"some text\" is a java.lang.String", instanceOf(Number.class), "some text");
    }

    @Test public void
    matchesPrimitiveTypes() {
        assertMatches(any(boolean.class), true);
        assertMatches(any(byte.class), (byte)1);
        assertMatches(any(char.class), 'x');
        assertMatches(any(double.class), 5.0);
        assertMatches(any(float.class), 5.0f);
        assertMatches(any(int.class), 2);
        assertMatches(any(long.class), 4L);
        assertMatches(any(short.class), (short)1);
    }

    @Test public void
    instanceOfRequiresACastToReturnTheCorrectTypeForUseInJMock() {
        @SuppressWarnings("unused")
        Integer anInteger = (Integer)with(instanceOf(Integer.class));
    }

    @Test public void
    anyWillReturnTheCorrectTypeForUseInJMock() {
        @SuppressWarnings("unused")
        Integer anInteger = with(any(Integer.class));
    }


    private static <T> T with(@SuppressWarnings("unused") Matcher<T> matcher) {
        return null;
    }
}

