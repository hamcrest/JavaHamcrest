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
import static org.hamcrest.core.DescribedAs.describedAs;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;

public final class DescribedAsTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<Object> matcher = describedAs("irrelevant", anything());

        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    overridesDescriptionOfOtherMatcherWithThatPassedToConstructor() {
        Matcher<?> matcher = describedAs("my description", anything());

        assertDescription("my description", matcher);
    }

    @Test public void
    appendsValuesToDescription() {
        Matcher<?> matcher = describedAs("value 1 = %0, value 2 = %1", anything(), 33, 97);

        assertDescription("value 1 = <33>, value 2 = <97>", matcher);
    }

    @Test public void
    celegatesMatchingToAnotherMatcher() {
        Matcher<String> matcher = describedAs("irrelevant", equalTo("hi"));

        assertMatches(matcher, "hi");
        assertDoesNotMatch("matched", matcher, "oi");
    }

    @Test public void
    delegatesMismatchDescriptionToAnotherMatcher() {
        Matcher<Integer> matcher = describedAs("irrelevant", equalTo(2));

        assertMismatchDescription("was <1>", matcher, 1);
    }
}
