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
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.internal.NullSafety;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.ArrayMatching.asEqualMatchers;

public class IsIterableContainingInOrder<E> extends TypeSafeDiagnosingMatcher<Iterable<? extends E>> {
    private final List<Matcher<? super E>> matchers;

    public IsIterableContainingInOrder(List<Matcher<? super E>> matchers) {
        this.matchers = matchers;
    }

    @Override
    protected boolean matchesSafely(Iterable<? extends E> iterable, Description mismatchDescription) {
        final MatchSeries<E> matchSeries = new MatchSeries<>(matchers, mismatchDescription);
        for (E item : iterable) {
            if (!matchSeries.matches(item)) {
                return false;
            }
        }

        return matchSeries.isFinished();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("iterable containing ").appendList("[", ", ", "]", matchers);
    }

    private static class MatchSeries<F> {
        private final List<Matcher<? super F>> matchers;
        private final Description mismatchDescription;
        private int nextMatchIx = 0;

        public MatchSeries(List<Matcher<? super F>> matchers, Description mismatchDescription) {
            this.mismatchDescription = mismatchDescription;
            if (matchers.isEmpty()) {
                throw new IllegalArgumentException("Should specify at least one expected element");
            }
            this.matchers = matchers;
        }

        public boolean matches(F item) {
          if (matchers.size() <= nextMatchIx) {
            mismatchDescription.appendText("not matched: ").appendValue(item);
            return false;
          }

          return isMatched(item);
        }

        public boolean isFinished() {
            if (nextMatchIx < matchers.size()) {
                mismatchDescription.appendText("no item was ").appendDescriptionOf(matchers.get(nextMatchIx));
                return false;
            }
            return true;
        }

        private boolean isMatched(F item) {
            final Matcher<? super F> matcher = matchers.get(nextMatchIx);
            if (!matcher.matches(item)) {
                describeMismatch(matcher, item);
                return false;
            }
            nextMatchIx++;
            return true;
        }

      private void describeMismatch(Matcher<? super F> matcher, F item) {
            mismatchDescription.appendText("item " + nextMatchIx + ": ");
            matcher.describeMismatch(item, mismatchDescription);
        }
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each logically equal to the
     * corresponding item in the specified items.  For a positive match, the examined iterable
     * must be of the same length as the number of specified items.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains("foo", "bar"))</pre>
     * 
     * @param items
     *     the items that must equal the items provided by an examined {@link Iterable}
     */
    @SafeVarargs
    public static <E> Matcher<Iterable<? extends E>> contains(E... items) {
        return contains(asEqualMatchers(items));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a single item that satisfies the specified matcher.
     * For a positive match, the examined iterable must only yield one item.
     * For example:
     * <pre>assertThat(Arrays.asList("foo"), contains(equalTo("foo")))</pre>
     * 
     * @param itemMatcher
     *     the matcher that must be satisfied by the single item provided by an
     *     examined {@link Iterable}
     */
    @SuppressWarnings("unchecked")
    public static <E> Matcher<Iterable<? extends E>> contains(final Matcher<? super E> itemMatcher) {
        return contains(new ArrayList<Matcher<? super E>>(singletonList(itemMatcher)));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each satisfying the corresponding
     * matcher in the specified matchers.  For a positive match, the examined iterable
     * must be of the same length as the number of specified matchers.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains(equalTo("foo"), equalTo("bar")))</pre>
     * 
     * @param itemMatchers
     *     the matchers that must be satisfied by the items provided by an examined {@link Iterable}
     */
    @SafeVarargs
    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... itemMatchers) {
        // required for JDK 1.6
        //noinspection RedundantTypeArguments
        final List<Matcher<? super E>> nullSafeWithExplicitTypeMatchers = NullSafety.<E>nullSafe(itemMatchers);
        return contains(nullSafeWithExplicitTypeMatchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each satisfying the corresponding
     * matcher in the specified list of matchers.  For a positive match, the examined iterable
     * must be of the same length as the specified list of matchers.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by the corresponding item provided by
     *     an examined {@link Iterable}
     */
    public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> itemMatchers) {
        return new IsIterableContainingInOrder<>(itemMatchers);
    }
}
