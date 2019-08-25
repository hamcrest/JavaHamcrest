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
package org.hamcrest;

/**
 * Utility class for writing one off matchers.
 * For example:
 * <pre>
 * Matcher&lt;String&gt; aNonEmptyString = new CustomMatcher&lt;String&gt;("a non empty string") {
 *   public boolean matches(Object object) {
 *     return ((object instanceof String) &amp;&amp; !((String) object).isEmpty();
 *   }
 * };
 * </pre>
 * <p>
 * This class is designed for scenarios where an anonymous inner class
 * matcher makes sense. It should not be used by API designers implementing
 * matchers.
 *
 * @author Neil Dunn
 * @param <T> The type of object being matched.
 * @see CustomTypeSafeMatcher for a type safe variant of this class that you probably
 *     want to use.
 */
public abstract class CustomMatcher<T> extends BaseMatcher<T> {
    private final String fixedDescription;

    public CustomMatcher(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description should be non null!");
        }
        this.fixedDescription = description;
    }

    @Override
    public final void describeTo(Description description) {
        description.appendText(fixedDescription);
    }
}
