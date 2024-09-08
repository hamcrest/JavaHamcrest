package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * @param <T> the collection element type
 * @deprecated As of release 2.1, replaced by {@link IsIterableContaining}.
 */
@Deprecated
public class IsCollectionContaining<T> extends TypeSafeDiagnosingMatcher<Iterable<? extends T>> {

    private final IsIterableContaining<T> delegate;

    /**
     * Constructor, best called from one of the static factory methods.
     * @param elementMatcher matches the expected element
     * @see #hasItem(Object)
     * @see #hasItem(Matcher)
     * @see #hasItems(Object[])
     * @see #hasItems(Matcher[])
     */
    public IsCollectionContaining(Matcher<? super T> elementMatcher) {
        this.delegate = new IsIterableContaining<>(elementMatcher);
    }

    @Override
    protected boolean matchesSafely(Iterable<? extends T> collection, Description mismatchDescription) {
        return delegate.matchesSafely(collection, mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        delegate.describeTo(description);
    }

    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields at least one item that is matched by the specified
     * <code>itemMatcher</code>.  Whilst matching, the traversal of the examined {@link Iterable}
     * will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem(startsWith("ba")))</pre>
     *
     * @deprecated As of version 2.1, use {@link IsIterableContaining#hasItem(Matcher)}.
     *
     * @param <T>
     *     the matcher type.
     * @param itemMatcher
     *     the matcher to apply to items provided by the examined {@link Iterable}
     * @return The matcher.
     */
    public static <T> Matcher<Iterable<? extends T>> hasItem(Matcher<? super T> itemMatcher) {
        return IsIterableContaining.hasItem(itemMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields at least one item that is equal to the specified
     * <code>item</code>.  Whilst matching, the traversal of the examined {@link Iterable}
     * will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem("bar"))</pre>
     *
     * @deprecated As of version 2.1, use {@link IsIterableContaining#hasItem(Object)}.
     * @param <T>
     *     the matcher type.
     * @param item
     *     the item to compare against the items provided by the examined {@link Iterable}
     * @return The matcher.
     */
    public static <T> Matcher<Iterable<? extends T>> hasItem(T item) {
        // Doesn't forward to hasItem() method so compiler can sort out generics.
        return IsIterableContaining.hasItem(item);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
     * examined {@link Iterable} yield at least one item that is matched by the corresponding
     * matcher from the specified <code>itemMatchers</code>.  Whilst matching, each traversal of
     * the examined {@link Iterable} will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems(endsWith("z"), endsWith("o")))</pre>
     *
     * @deprecated As of version 2.1, use {@link IsIterableContaining#hasItems(Matcher[])}}.
     * @param <T>
     *     the matcher type.
     * @param itemMatchers
     *     the matchers to apply to items provided by the examined {@link Iterable}
     * @return The matcher.
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> hasItems(Matcher<? super T>... itemMatchers) {
        return IsIterableContaining.hasItems(itemMatchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
     * examined {@link Iterable} yield at least one item that is equal to the corresponding
     * item from the specified <code>items</code>.  Whilst matching, each traversal of the
     * examined {@link Iterable} will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems("baz", "foo"))</pre>
     *
     * @deprecated As of version 2.1, use {@link IsIterableContaining#hasItems(Object[])}}.
     * @param <T>
     *     the matcher type.
     * @param items
     *     the items to compare against the items provided by the examined {@link Iterable}
     * @return The matcher.
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> hasItems(T... items) {
        return IsIterableContaining.hasItems(items);
    }

}
