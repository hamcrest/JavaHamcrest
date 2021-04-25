package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;

public class IsIterableIntersecting<T> extends TypeSafeDiagnosingMatcher<Iterable<? extends T>> {
    private final Collection<Matcher<? super T>> matchers;

    public IsIterableIntersecting(Collection<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    protected boolean matchesSafely(Iterable<? extends T> items, Description mismatchDescription) {
        final Matching<T> matching = new Matching<>(matchers, mismatchDescription);
        for (T item : items) {
            if (matching.matches(item)) {
                return true;
            }
        }

        return matching.isFinished(items);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("iterable with items ")
                .appendList("[", ", ", "]", matchers)
                .appendText(" intersecting");
    }

    private static class Matching<S> {
        private final Collection<Matcher<? super S>> matchers;
        private final Description mismatchDescription;

        private Matching(Collection<Matcher<? super S>> matchers, Description mismatchDescription) {
            this.matchers = new ArrayList<>(matchers);
            this.mismatchDescription = mismatchDescription;
        }

        public boolean matches(S item) {
            if (matchers.isEmpty()) {
                mismatchDescription.appendText("no match for: ").appendValue(item);
                return false;
            }
            return isMatched(item);
        }

        public boolean isFinished(Iterable<? extends S> items) {
            if (matchers.isEmpty()) {
                return true;
            }
            mismatchDescription
                    .appendText("no item intersects: ").appendList("", ", ", "", matchers)
                    .appendText(" with ").appendValueList("[", ", ", "]", items);
            return false;
        }

        private boolean isMatched(S item) {
            for (Matcher<? super S>  matcher : matchers) {
                if (matcher.matches(item)) {
                    matchers.clear();
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items. For a positive match, the examined
     * iterable must have  have at least one item in common with specified matchers.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat("Intersect with", Arrays.asList(1, 2, 3, 4), intersectWith(equalTo(2), equalTo(3)));</pre>
     *
     * @param itemMatchers
     *     the matchers that must be satisfied by the items provided by an examined {@link Iterable} in the same relative order
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> intersectWith(Matcher<? super T>... itemMatchers) {
        return intersectWith(Arrays.asList(itemMatchers));
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items. For a positive match, the examined
     * iterable must have  have at least one item in common with specified matchers.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat("Intersect with", Arrays.asList(1, 2, 3, 4), intersectWith(2, 3));</pre>
     *
     * @param items
     *     the items that must equal the items provided by an examined {@link Iterable} in any order
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> intersectWith(T... items) {
        List<Matcher<? super T>> matchers = new ArrayList<>();
        for (T item : items) {
            matchers.add(equalTo(item));
        }
        return new IsIterableIntersecting<>(matchers);
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items. For a positive match, the examined
     * iterable must have  have at least one item in common with specified matchers.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat("Intersect with", Arrays.asList(1, 2, 3, 4), intersectWith(Arrays.asList(equalTo(2), equalTo(3))));</pre>
     *
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by the items provided by
     *     an examined {@link Iterable} in the same relative order
     */
    public static <T> Matcher<Iterable<? extends T>> intersectWith(Collection<Matcher<? super T>> itemMatchers) {
        return new IsIterableIntersecting<>(itemMatchers);
    }
}
