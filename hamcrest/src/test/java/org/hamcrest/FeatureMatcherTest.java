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
package org.hamcrest;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.junit.Assert.assertEquals;

public final class FeatureMatcherTest {
    private final FeatureMatcher<Thingy, String> resultMatcher = resultMatcher();

    @Test public void
    matchesPartOfAnObject() {
        assertMatches("feature", resultMatcher, new Thingy("bar"));
        assertDescription("Thingy with result \"bar\"", resultMatcher);
    }

    @Test public void
    mismatchesPartOfAnObject() {
        assertMismatchDescription("result mismatch-description", resultMatcher, new Thingy("foo"));
    }

    @Test public void
    doesNotThrowNullPointerException() {
        assertMismatchDescription("was null", resultMatcher, null);
    }

    @Test public void
    doesNotThrowClassCastException() {
        resultMatcher.matches(new ShouldNotMatch());
        StringDescription mismatchDescription = new StringDescription(); 
        resultMatcher.describeMismatch(new ShouldNotMatch(), mismatchDescription);
        assertEquals("was ShouldNotMatch <ShouldNotMatch>", mismatchDescription.toString());
    }

    public static class Match extends IsEqual<String> {
        public Match(String equalArg) { super(equalArg); }
        @Override public void describeMismatch(Object item, Description description) {
            description.appendText("mismatch-description");
        }
    }

    public static class Thingy {
        private final String result;

        public Thingy(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }

    public static class ShouldNotMatch {
        @Override public String toString() { return "ShouldNotMatch"; }
    } 

    private static FeatureMatcher<Thingy, String> resultMatcher() {
        return new FeatureMatcher<Thingy, String>(new Match("bar"), "Thingy with result", "result") {
            @Override
            public String featureValueOf(Thingy actual) {
                return actual.getResult();
            }
        };
    }

}
