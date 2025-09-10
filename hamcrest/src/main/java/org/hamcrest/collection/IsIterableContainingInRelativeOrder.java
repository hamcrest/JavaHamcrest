package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.List;
import org.hamcrest.collection.IsIterableContainingParallelRuns.MatchParallelRuns;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Tests if an iterable contains matching elements in relative order.
 *
 * @param <E> the type of items in the iterable.
 */
public class IsIterableContainingInRelativeOrder<E> extends TypeSafeDiagnosingMatcher<Iterable<? extends E>> {
    private final List<Matcher<? super E>> matchers;

    /**
     * Constructor, best called from {@link #containsInRelativeOrder(Object[])} ,
     * {@link #containsInRelativeOrder(Matcher[])}, or {@link #containsInRelativeOrder(List)}.
     * @param matchers the matchers
     */
    public IsIterableContainingInRelativeOrder(List<Matcher<? super E>> matchers) {
        this.matchers = matchers;
    }

    @Override
    protected boolean matchesSafely(Iterable<? extends E> iterable, Description mismatchDescription) {
        final MatchParallelRuns<E> matchParallelRuns =
            new MatchParallelRuns<>(1, matchers, mismatchDescription);
        matchParallelRuns.processItems(iterable);
        return matchParallelRuns.isFinished();
    }

    public void describeTo(Description description) {
        description.appendText("iterable containing ").appendList("[", ", ", "]", matchers).appendText(" in relative order");
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, that contains items logically equal to the
     * corresponding item in the specified items, in the same relative order
     * For example:
     * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), containsInRelativeOrder("b", "d"))</pre>
     *
     * @param <E>
     *     the matcher type.
     * @param items
     *     the items that must be contained within items provided by an examined {@link Iterable} in the same relative order
     * @return The matcher.
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
     * @param <E>
     *     the matcher type.
     * @param itemMatchers
     *     the matchers that must be satisfied by the items provided by an examined {@link Iterable} in the same relative order
     * @return The matcher.
     */
    @SafeVarargs
    public static <E> Matcher<Iterable<? extends E>> containsInRelativeOrder(Matcher<? super E>... itemMatchers) {
        return containsInRelativeOrder((List) asList(itemMatchers));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, that contains items satisfying the corresponding
     * matcher in the specified list of matchers, in the same relative order.
     * For example:
     * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), contains(Arrays.asList(equalTo("b"), equalTo("d"))))</pre>
     *
     * @param <E>
     *     the matcher type.
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by the items provided by
     *     an examined {@link Iterable} in the same relative order
     * @return The matcher.
     */
    public static <E> Matcher<Iterable<? extends E>> containsInRelativeOrder(List<Matcher<? super E>> itemMatchers) {
        return new IsIterableContainingInRelativeOrder<>(itemMatchers);
    }

}
