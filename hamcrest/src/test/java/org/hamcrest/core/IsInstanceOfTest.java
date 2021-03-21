/**
 * BSD License
 *
 * Copyright (c) 2000-2021 www.hamcrest.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * Neither the name of Hamcrest nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
        assertUnknownTypeSafe(matcher);
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

