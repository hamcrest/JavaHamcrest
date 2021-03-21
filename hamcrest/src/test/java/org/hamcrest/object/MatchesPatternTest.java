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
package org.hamcrest.object;

import org.hamcrest.Matcher;
import org.hamcrest.text.MatchesPattern;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.junit.Assert.assertThat;

public class MatchesPatternTest {
    @Test
    public void copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = new MatchesPattern(Pattern.compile("."));

        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test
    public void matchesExactString() {
        assertThat("a", new MatchesPattern(Pattern.compile("a")));
    }

    @Test
    public void doesNotMatchADifferentString() {
        assertDoesNotMatch("A different string does not match", new MatchesPattern(Pattern.compile("a")), "b");
    }

    @Test
    public void doesNotMatchSubstring() {
        assertDoesNotMatch("A substring does not match", new MatchesPattern(Pattern.compile("a")), "ab");
    }

    @Test
    public void hasAReadableDescription() {
        Matcher<?> m = new MatchesPattern(Pattern.compile("a[bc](d|e)"));
        assertDescription("a string matching the pattern 'a[bc](d|e)'", m );
    }

    @Test
    public void describesAMismatch() {
        final Matcher<String> matcher = new MatchesPattern(Pattern.compile("a"));
        assertMismatchDescription("was \"Cheese\"", matcher, "Cheese");
    }

    @Test
    public void factoryMethodAllowsCreationWithPattern() {
        Matcher<?> m = MatchesPattern.matchesPattern(Pattern.compile("a[bc](d|e)"));
        assertDescription("a string matching the pattern 'a[bc](d|e)'", m );
    }

    @Test
    public void factoryMethodAllowsCreationWithString() {
        Matcher<?> m = MatchesPattern.matchesPattern("a[bc](d|e)");
        assertDescription("a string matching the pattern 'a[bc](d|e)'", m );
    }
}
