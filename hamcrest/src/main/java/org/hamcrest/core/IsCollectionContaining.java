package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * @deprecated As of release 2.1, replaced by {@link IsIterableContaining}.
 */
@Deprecated
public class IsCollectionContaining<T> extends TypeSafeDiagnosingMatcher<Iterable<? super T>> {
    private final IsIterableContaining<T> delegate;

    public IsCollectionContaining(Matcher<? super T> elementMatcher) {
        this.delegate = new IsIterableContaining<>(elementMatcher);
    }

    @Override
    protected boolean matchesSafely(Iterable<? super T> collection, Description mismatchDescription) {
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
     * @param itemMatcher
     *     the matcher to apply to items provided by the examined {@link Iterable}
     */
    public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> itemMatcher) {
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
     *
     * @param item
     *     the item to compare against the items provided by the examined {@link Iterable}
     */
    public static <T> Matcher<Iterable<? super T>> hasItem(T item) {
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
     *
     * @param itemMatchers
     *     the matchers to apply to items provided by the examined {@link Iterable}
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... itemMatchers) {
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
     *
     * @param items
     *     the items to compare against the items provided by the examined {@link Iterable}
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<T>> hasItems(T... items) {
        return IsIterableContaining.hasItems(items);
    }

}
