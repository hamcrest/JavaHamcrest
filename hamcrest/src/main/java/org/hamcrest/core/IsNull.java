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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.core.IsNot.not;

/**
 * Is the value null?
 */
public class IsNull<T> extends BaseMatcher<T> {
    @Override
    public boolean matches(Object o) {
        return o == null;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("null");
    }

    /**
     * Creates a matcher that matches if examined object is <code>null</code>.
     * For example:
     * <pre>assertThat(cheese, is(nullValue())</pre>
     * 
     */
    public static Matcher<Object> nullValue() {
        return new IsNull<Object>();
    }

    /**
     * A shortcut to the frequently used <code>not(nullValue())</code>.
     * For example:
     * <pre>assertThat(cheese, is(notNullValue()))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(not(nullValue())))</pre>
     * 
     */
    public static Matcher<Object> notNullValue() {
        return not(nullValue());
    }

    /**
     * Creates a matcher that matches if examined object is <code>null</code>. Accepts a
     * single dummy argument to facilitate type inference.
     * For example:
     * <pre>assertThat(cheese, is(nullValue(Cheese.class))</pre>
     * 
     * @param type
     *     dummy parameter used to infer the generic type of the returned matcher
     */
    public static <T> Matcher<T> nullValue(Class<T> type) {
        return new IsNull<T>();
    }

    /**
     * A shortcut to the frequently used <code>not(nullValue(X.class)). Accepts a
     * single dummy argument to facilitate type inference.</code>.
     * For example:
     * <pre>assertThat(cheese, is(notNullValue(X.class)))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(not(nullValue(X.class))))</pre>
     * 
     * @param type
     *     dummy parameter used to infer the generic type of the returned matcher
     *  
     */
    public static <T> Matcher<T> notNullValue(Class<T> type) {
        return not(nullValue(type));
    }
}

