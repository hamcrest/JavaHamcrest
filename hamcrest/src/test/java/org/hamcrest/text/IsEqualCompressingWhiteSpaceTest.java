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

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.text.IsEqualCompressingWhiteSpace.equalToCompressingWhiteSpace;

public class IsEqualCompressingWhiteSpaceTest extends AbstractMatcherTest {

    private final Matcher<String> matcher = equalToCompressingWhiteSpace(" Hello World   how\n are we? ");

    @Override
    protected Matcher<?> createMatcher() {
        return matcher;
    }

    public void testPassesIfWordsAreSameButWhitespaceDiffers() {
        assertMatches(matcher, "Hello World how are we?");
        assertMatches(matcher, "   Hello World   how are \n\n\twe?");
    }

    public void testFailsIfTextOtherThanWhitespaceDiffers() {
        assertDoesNotMatch(matcher, "Hello PLANET how are we?");
        assertDoesNotMatch(matcher, "Hello World how are we");
    }

    public void testFailsIfWhitespaceIsAddedOrRemovedInMidWord() {
        assertDoesNotMatch(matcher, "HelloWorld how are we?");
        assertDoesNotMatch(matcher, "Hello Wo rld how are we?");
    }

    public void test_has_a_readable_mismatch() {
        assertMismatchDescription("was \"Hello World how are we \"", matcher, "Hello World how are we ");
    }

    public void testFailsIfMatchingAgainstNull() {
        assertDoesNotMatch(matcher, null);
    }

    public void testHasAReadableDescription() {
        assertDescription("a string equal to \" Hello World   how\\n are we? \" compressing white space",
                        matcher);
    }

    public void testPassesIfWhitespacesContainsNoBreakSpace() {
        assertMatches(matcher, "Hello" + ((char)160) + "World how are we?");
    }
}
