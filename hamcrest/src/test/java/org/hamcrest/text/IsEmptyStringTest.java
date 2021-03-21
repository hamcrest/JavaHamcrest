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
package org.hamcrest.text;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.hamcrest.text.IsEmptyString.emptyString;

public final class IsEmptyStringTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = emptyString();
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    matchesEmptyString() {
        assertMatches(emptyOrNullString(), "");
        assertMatches(emptyString(), "");
    }

    @Test public void
    matchesNullAppropriately() {
        assertMatches(emptyOrNullString(), null);
        assertDoesNotMatch(emptyString(), null);
    }

    @Test public void
    matchesBlankStringAppropriately() {
        assertDoesNotMatch(emptyString(), "  ");
        assertDoesNotMatch(emptyOrNullString(), "  ");
    }

    @Test public void
    doesNotMatchFilledString() {
        assertDoesNotMatch(emptyString(), "a");
        assertDoesNotMatch(emptyOrNullString(), "a");
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
