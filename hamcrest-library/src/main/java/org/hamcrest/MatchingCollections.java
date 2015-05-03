package org.hamcrest;

import org.hamcrest.collection.IsCollectionContaining;

/**
 * @author 2015 http://www.hamcrest.com
 */
public class MatchingCollections {
    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields at least one item that is matched by the specified
     * <code>itemMatcher</code>.  Whilst matching, the traversal of the examined {@link Iterable}
     * will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem(startsWith("ba")))</pre>
     *
     * @param itemMatcher
     *     the matcher to apply to items provided by the examined {@link Iterable}
     */
    public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> itemMatcher) {
      return IsCollectionContaining.hasItem(itemMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields at least one item that is equal to the specified
     * <code>item</code>.  Whilst matching, the traversal of the examined {@link Iterable}
     * will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem("bar"))</pre>
     *
     * @param item
     *     the item to compare against the items provided by the examined {@link Iterable}
     */
    public static <T> Matcher<Iterable<? super T>> hasItem(T item) {
      return IsCollectionContaining.hasItem(item);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
     * examined {@link Iterable} yield at least one item that is matched by the corresponding
     * matcher from the specified <code>itemMatchers</code>.  Whilst matching, each traversal of
     * the examined {@link Iterable} will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems(endsWith("z"), endsWith("o")))</pre>
     *
     * @param itemMatchers
     *     the matchers to apply to items provided by the examined {@link Iterable}
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... itemMatchers) {
      return IsCollectionContaining.hasItems(itemMatchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
     * examined {@link Iterable} yield at least one item that is equal to the corresponding
     * item from the specified <code>items</code>.  Whilst matching, each traversal of the
     * examined {@link Iterable} will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems("baz", "foo"))</pre>
     *
     * @param items
     *     the items to compare against the items provided by the examined {@link Iterable}
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<T>> hasItems(T... items) {
      return IsCollectionContaining.hasItems(items);
    }
}
