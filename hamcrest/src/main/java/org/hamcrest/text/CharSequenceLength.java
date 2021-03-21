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

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author Marco Leichsenring
 * @author Steve Freeman
 */
public class CharSequenceLength extends FeatureMatcher<CharSequence, Integer> {

    /**
     * @param lengthMatcher         The matcher to apply to the feature
     */
    @SuppressWarnings("WeakerAccess")
    public CharSequenceLength(Matcher<? super Integer> lengthMatcher) {
        super(lengthMatcher, "a CharSequence with length", "length");
    }

    @Override
    protected Integer featureValueOf(CharSequence actual) {
        return actual.length();
    }

    /**
     * Creates a matcher of {@link CharSequence} that matches when a char sequence has the given length
     * For example:
     * 
     * <pre>
     * assertThat("text", hasLength(4))
     * </pre>
     * 
     * @param length the expected length of the string
     */
    public static Matcher<CharSequence> hasLength(int length) {
        return new CharSequenceLength(equalTo(length));
    }

    /**
      * Creates a matcher of {@link CharSequence} that matches when a char sequence has the given length
      * For example:
      *
      * <pre>
      * assertThat("text", hasLength(lessThan(4)))
      * </pre>
      *
      * @param lengthMatcher the expected length of the string
      */
     @SuppressWarnings("WeakerAccess")
     public static Matcher<CharSequence> hasLength(Matcher<? super Integer> lengthMatcher) {
         return new CharSequenceLength(lengthMatcher);
     }
}


