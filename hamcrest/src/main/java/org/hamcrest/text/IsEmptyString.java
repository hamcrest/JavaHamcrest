/**
 * BSD License
 *
 * Copyright (c) 2000-2015 www.hamcrest.org
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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Matches empty Strings (and null).
 */
public final class IsEmptyString extends TypeSafeMatcher<String> {
    private static final IsEmptyString INSTANCE = new IsEmptyString();
    @SuppressWarnings("unchecked")
    private static final Matcher<String> NULL_OR_EMPTY_INSTANCE = anyOf(nullValue(), INSTANCE);

    private IsEmptyString() { }

    @Override
    public boolean matchesSafely(String item) {
        return item.equals("");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an empty string");
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string has zero length.
     * For example:
     * <pre>assertThat("", isEmptyString())</pre>
     *
     * @return the created matcher
     * @deprecated use is(emptyString()) instead
     */
    @Deprecated
    public static Matcher<String> isEmptyString() {
        return emptyString();
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string has zero length.
     * For example:
     * <pre>assertThat("", is(emptyString()))</pre>
     *
     * @return the created matcher
     */
    public static Matcher<String> emptyString() {
        return INSTANCE;
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * has zero length.
     * For example:
     * <pre>assertThat(((String)null), isEmptyOrNullString())</pre>
     *
     * @return the created matcher
     * @deprecated use is(emptyOrNullString()) instead
     */
    @Deprecated
    public static Matcher<String> isEmptyOrNullString() {
        return emptyOrNullString();
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * has zero length.
     * For example:
     * <pre>assertThat(((String)null), is(emptyOrNullString()))</pre>
     *
     * @return the created matcher
     */
    public static Matcher<String> emptyOrNullString() {
        return NULL_OR_EMPTY_INSTANCE;
    }
}
