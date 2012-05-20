package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsIterableContainingInOrder<E> extends TypeSafeDiagnosingMatcher<Iterable<? extends E>> {
    private final List<Matcher<? super E>> matchers;

    public IsIterableContainingInOrder(List<Matcher<? super E>> matchers) {
        this.matchers = matchers;
    }

    @Override
    protected boolean matchesSafely(Iterable<? extends E> iterable, Description mismatchDescription) {
        MatchSeries<E> matchSeries = new MatchSeries<E>(matchers, mismatchDescription);
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
        public final List<Matcher<? super F>> matchers;
        private final Description mismatchDescription;
        public int nextMatchIx = 0;

        public MatchSeries(List<Matcher<? super F>> matchers, Description mismatchDescription) {
            this.mismatchDescription = mismatchDescription;
            if (matchers.isEmpty()) {
                throw new IllegalArgumentException("Should specify at least one expected element");
            }
            this.matchers = matchers;
        }

        public boolean matches(F item) {
            return isNotSurplus(item) && isMatched(item);
        }

        public boolean isFinished() {
            if (nextMatchIx < matchers.size()) {
                mismatchDescription.appendText("No item matched: ").appendDescriptionOf(matchers.get(nextMatchIx));
                return false;
            }
            return true;
        }

        private boolean isMatched(F item) {
            Matcher<? super F> matcher = matchers.get(nextMatchIx);
            if (!matcher.matches(item)) {
                describeMismatch(matcher, item);
                return false;
            }
            nextMatchIx++;
            return true;
        }

        private boolean isNotSurplus(F item) {
            if (matchers.size() <= nextMatchIx) {
                mismatchDescription.appendText("Not matched: ").appendValue(item);
                return false;
            }
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
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains("foo", "bar"))</pre>
     * 
     * @param items
     *     the items that must equal the items provided by an examined {@link Iterable}
     */
    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(E... items) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        for (E item : items) {
            matchers.add(equalTo(item));
        }

        return contains(matchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a single item that satisfies the specified matcher.
     * For a positive match, the examined iterable must only yield one item.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo"), contains(equalTo("foo")))</pre>
     * 
     * @param itemMatcher
     *     the matcher that must be satisfied by the single item provided by an
     *     examined {@link Iterable}
     */
    @SuppressWarnings("unchecked")
    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(final Matcher<? super E> itemMatcher) {
        return contains(new ArrayList<Matcher<? super E>>(asList(itemMatcher)));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each satisfying the corresponding
     * matcher in the specified matchers.  For a positive match, the examined iterable
     * must be of the same length as the number of specified matchers.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains(equalTo("foo"), equalTo("bar")))</pre>
     * 
     * @param itemMatchers
     *     the matchers that must be satisfied by the items provided by an examined {@link Iterable}
     */
    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... itemMatchers) {
        return contains(asList(itemMatchers));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each satisfying the corresponding
     * matcher in the specified list of matchers.  For a positive match, the examined iterable
     * must be of the same length as the specified list of matchers.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by the corresponding item provided by
     *     an examined {@link Iterable}
     */
    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> itemMatchers) {
        return new IsIterableContainingInOrder<E>(itemMatchers);
    }
}
