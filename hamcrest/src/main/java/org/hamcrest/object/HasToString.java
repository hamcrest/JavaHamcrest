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

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.core.IsEqual.equalTo;

public class HasToString<T> extends FeatureMatcher<T, String> {
    public HasToString(Matcher<? super String> toStringMatcher) {
      super(toStringMatcher, "with toString()", "toString()");
    }
    
    @Override
    protected String featureValueOf(T actual) {
      return String.valueOf(actual);
    }

    /**
     * Creates a matcher that matches any examined object whose <code>toString</code> method
     * returns a value that satisfies the specified matcher.
     * For example:
     * <pre>assertThat(true, hasToString(equalTo("TRUE")))</pre>
     * 
     * @param toStringMatcher
     *     the matcher used to verify the toString result
     */
    public static <T> Matcher<T> hasToString(Matcher<? super String> toStringMatcher) {
        return new HasToString<T>(toStringMatcher);
    }

    /**
     * Creates a matcher that matches any examined object whose <code>toString</code> method
     * returns a value equalTo the specified string.
     * For example:
     * <pre>assertThat(true, hasToString("TRUE"))</pre>
     * 
     * @param expectedToString
     *     the expected toString result
     */
    public static <T> Matcher<T> hasToString(String expectedToString) {
        return new HasToString<T>(equalTo(expectedToString));
    }
}
