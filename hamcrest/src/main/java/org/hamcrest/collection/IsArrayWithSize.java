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
package org.hamcrest.collection;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.core.DescribedAs.describedAs;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matches if array size satisfies a nested matcher.
 */
public class IsArrayWithSize<E> extends FeatureMatcher<E[], Integer> {
    public IsArrayWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "an array with size","array size");
    }

    @Override
    protected Integer featureValueOf(E[] actual) {
      return actual.length;
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * satisfies the specified matcher.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayWithSize(equalTo(2)))</pre>
     * 
     * @param sizeMatcher
     *     a matcher for the length of an examined array
     */
    public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsArrayWithSize<>(sizeMatcher);
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * equals the specified <code>size</code>.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayWithSize(2))</pre>
     * 
     * @param size
     *     the length that an examined array must have for a positive match
     */
    public static <E> Matcher<E[]> arrayWithSize(int size) {
        return arrayWithSize(equalTo(size));
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * is zero.
     * For example:
     * <pre>assertThat(new String[0], emptyArray())</pre>
     * 
     */
    public static <E> Matcher<E[]> emptyArray() {
        return describedAs("an empty array", IsArrayWithSize.<E>arrayWithSize(0));
    }
}
