package org.hamcrest;

import org.hamcrest.collection.*;

import java.util.Collection;
import java.util.List;

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
        return IsIterableContainingInOrder.contains(items);
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
    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> itemMatcher) {
        return IsIterableContainingInOrder.contains(itemMatcher);
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
        return IsIterableContainingInOrder.contains(itemMatchers);
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
        return IsIterableContainingInOrder.contains(itemMatchers);
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
     * in the specified matchers.  For a positive match, the examined iterable must be of the same
     * length as the number of specified matchers.
     * </p>
     * <p>
     * N.B. each of the specified matchers will only be used once during a given examination, so be
     * careful when specifying matchers that may be satisfied by more than one entry in an examined
     * iterable.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(equalTo("bar"), equalTo("foo")))</pre>
     *
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... itemMatchers) {
        return IsIterableContainingInAnyOrder.containsInAnyOrder(itemMatchers);
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each logically equal to one item
     * anywhere in the specified items. For a positive match, the examined iterable
     * must be of the same length as the number of specified items.
     * </p>
     * <p>
     * N.B. each of the specified items will only be used once during a given examination, so be
     * careful when specifying items that may be equal to more than one entry in an examined
     * iterable.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder("bar", "foo"))</pre>
     *
     * @param items
     *     the items that must equal the items provided by an examined {@link Iterable} in any order
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... items) {
        return IsIterableContainingInAnyOrder.containsInAnyOrder(items);
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
     * in the specified collection of matchers.  For a positive match, the examined iterable
     * must be of the same length as the specified collection of matchers.
     * </p>
     * <p>
     * N.B. each matcher in the specified collection will only be used once during a given
     * examination, so be careful when specifying matchers that may be satisfied by more than
     * one entry in an examined iterable.
     * </p>
     * <p>For example:</p>
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(Arrays.asList(equalTo("bar"), equalTo("foo"))))</pre>
     *
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     */
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> itemMatchers) {
        return IsIterableContainingInAnyOrder.<T>containsInAnyOrder(itemMatchers);
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
        return IsIterableContainingInRelativeOrder.containsInRelativeOrder(items);
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
        return IsIterableContainingInRelativeOrder.containsInRelativeOrder(itemMatchers);
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
        return IsIterableContainingInRelativeOrder.<E>containsInRelativeOrder(itemMatchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields an item count that satisfies the specified
     * matcher.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(equalTo(2)))</pre>
     *
     * @param sizeMatcher
     *     a matcher for the number of items that should be yielded by an examined {@link Iterable}
     */
    public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> sizeMatcher) {
        return IsIterableWithSize.<E>iterableWithSize(sizeMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields an item count that is equal to the specified
     * <code>size</code> argument.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(2))</pre>
     *
     * @param size
     *     the number of items that should be yielded by an examined {@link Iterable}
     */
    public static <E> Matcher<Iterable<E>> iterableWithSize(int size) {
        return IsIterableWithSize.<E>iterableWithSize(size);
    }
}
