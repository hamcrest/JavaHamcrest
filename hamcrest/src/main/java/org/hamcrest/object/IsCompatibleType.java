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
package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher for class compatibility.
 * @param <T> the matcher type
 */
public class IsCompatibleType<T> extends TypeSafeMatcher<Class<?>> {
    private final Class<T> type;

    public IsCompatibleType(Class<T> type) {
        this.type = type;
    }

    @Override
    public boolean matchesSafely(Class<?> cls) {
        return type.isAssignableFrom(cls);
    }

    @Override
    public void describeMismatchSafely(Class<?> cls, Description mismatchDescription) {
        mismatchDescription.appendValue(cls.getName());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("type < ").appendText(type.getName());
    }

    /**
     * Creates a matcher of {@link Class} that matches when the specified baseType is
     * assignable from the examined class.
     * For example:
     * <pre>assertThat(Integer.class, typeCompatibleWith(Number.class))</pre>
     *
     * @param <T> the type to compare to
     * @param baseType
     *     the base class to examine classes against
     * @return the created matcher
     */
    public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> baseType) {
        return new IsCompatibleType<>(baseType);
    }
}
