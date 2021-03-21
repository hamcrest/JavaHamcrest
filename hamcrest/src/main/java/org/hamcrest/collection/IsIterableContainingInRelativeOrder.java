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

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsIterableContainingInRelativeOrder<E> extends TypeSafeDiagnosingMatcher<Iterable<? extends E>> {
    private final List<Matcher<? super E>> matchers;

    public IsIterableContainingInRelativeOrder(List<Matcher<? super E>> matchers) {
        this.matchers = matchers;
    }

    @Override
    protected boolean matchesSafely(Iterable<? extends E> iterable, Description mismatchDescription) {
        MatchSeriesInRelativeOrder<E> matchSeriesInRelativeOrder = new MatchSeriesInRelativeOrder<>(matchers, mismatchDescription);
        matchSeriesInRelativeOrder.processItems(iterable);
        return matchSeriesInRelativeOrder.isFinished();
    }

    public void describeTo(Description description) {
        description.appendText("iterable containing ").appendList("[", ", ", "]", matchers).appendText(" in relative order");
    }

    private static class MatchSeriesInRelativeOrder<F> {
        public final List<Matcher<? super F>> matchers;
        private final Description mismatchDescription;
        private int nextMatchIx = 0;
        private F lastMatchedItem = null;

        public MatchSeriesInRelativeOrder(List<Matcher<? super F>> matchers, Description mismatchDescription) {
            this.mismatchDescription = mismatchDescription;
            if (matchers.isEmpty()) {
                throw new IllegalArgumentException("Should specify at least one expected element");
            }
            this.matchers = matchers;
        }

        public void processItems(Iterable<? extends F> iterable) {
            for (F item : iterable) {
                if (nextMatchIx < matchers.size()) {
                    Matcher<? super F> matcher = matchers.get(nextMatchIx);
                    if (matcher.matches(item)) {
                        lastMatchedItem = item;
                        nextMatchIx++;
                    }
                }
            }
        }

        public boolean isFinished() {
            if (nextMatchIx < matchers.size()) {
                mismatchDescription.appendDescriptionOf(matchers.get(nextMatchIx)).appendText(" was not found");
                if (lastMatchedItem != null) {
                    mismatchDescription.appendText(" after ").appendValue(lastMatchedItem);
                }
                return false;
            }
            return true;
        }

    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, that contains items logically equal to the
     * corresponding item in the specified items, in the same relative order
     * For example:
     * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), containsInRelativeOrder("b", "d"))</pre>
     * 
     * @param items
     *     the items that must be contained within items provided by an examined {@link Iterable} in the same relative order
     */
    @SafeVarargs
    public static <E> Matcher<Iterable<? extends E>> containsInRelativeOrder(E... items) {
        List<Matcher<? super E>> matchers = new ArrayList<>();
        for (E item : items) {
            matchers.add(equalTo(item));
        }

        return containsInRelativeOrder(matchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, that each satisfying the corresponding
     * matcher in the specified matchers, in the same relative order.
     * For example:
     * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), containsInRelativeOrder(equalTo("b"), equalTo("d")))</pre>
     * 
     * @param itemMatchers
     *     the matchers that must be satisfied by the items provided by an examined {@link Iterable} in the same relative order
     */
    @SafeVarargs
    public static <E> Matcher<Iterable<? extends E>> containsInRelativeOrder(Matcher<? super E>... itemMatchers) {
        return containsInRelativeOrder(asList(itemMatchers));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, that contains items satisfying the corresponding
     * matcher in the specified list of matchers, in the same relative order.
     * For example:
     * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), contains(Arrays.asList(equalTo("b"), equalTo("d"))))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by the items provided by
     *     an examined {@link Iterable} in the same relative order
     */
    public static <E> Matcher<Iterable<? extends E>> containsInRelativeOrder(List<Matcher<? super E>> itemMatchers) {
        return new IsIterableContainingInRelativeOrder<>(itemMatchers);
    }
}
