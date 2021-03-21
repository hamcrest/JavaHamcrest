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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @deprecated As of release 2.1, replaced by {@link ArrayMatching}.
 */
public class IsArrayContainingInOrder<E> extends TypeSafeMatcher<E[]> {
    private final Collection<Matcher<? super E>> matchers;
    private final IsIterableContainingInOrder<E> iterableMatcher;

    public IsArrayContainingInOrder(List<Matcher<? super E>> matchers) {
        this.iterableMatcher = new IsIterableContainingInOrder<E>(matchers);
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(E[] item) {
        return iterableMatcher.matches(asList(item));
    }
    
    @Override
    public void describeMismatchSafely(E[] item, Description mismatchDescription) {
      iterableMatcher.describeMismatch(asList(item), mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("[", ", ", "]", matchers);
    }

    /**
     * Creates a matcher for arrays that matcheswhen each item in the examined array is
     * logically equal to the corresponding item in the specified items.  For a positive match,
     * the examined array must be of the same length as the number of specified items.
     * <p>
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, contains("foo", "bar"))</pre>
     *
     * @deprecated As of version 2.1, use {@link ArrayMatching#arrayContaining(Object[])}.
     *
     * @param items
     *     the items that must equal the items within an examined array
     */
    public static <E> Matcher<E[]> arrayContaining(E... items) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        for (E item : items) {
            matchers.add(equalTo(item));
        }
        return arrayContaining(matchers);
    }

    /**
     * Creates a matcher for arrays that matches when each item in the examined array satisfies the
     * corresponding matcher in the specified matchers.  For a positive match, the examined array
     * must be of the same length as the number of specified matchers.
     * <p>
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, contains(equalTo("foo"), equalTo("bar")))</pre>
     *
     * @deprecated As of version 2.1, use {@link ArrayMatching#arrayContaining(Matcher[])}.
     *
     * @param itemMatchers
     *     the matchers that must be satisfied by the items in the examined array
     */
    public static <E> Matcher<E[]> arrayContaining(Matcher<? super E>... itemMatchers) {
        return arrayContaining(asList(itemMatchers));
    }

    /**
     * Creates a matcher for arrays that matches when each item in the examined array satisfies the
     * corresponding matcher in the specified list of matchers.  For a positive match, the examined array
     * must be of the same length as the specified list of matchers.
     * <p>
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, contains(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
     *
     * @deprecated As of version 2.1, use {@link ArrayMatching#arrayContaining(List)}.
     *
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by the corresponding item in an examined array
     */
    public static <E> Matcher<E[]> arrayContaining(List<Matcher<? super E>> itemMatchers) {
        return new IsArrayContainingInOrder<E>(itemMatchers);
    }
}
