package org.hamcrest.core;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsCollectionContaining<I extends Iterable<?>> extends TypeSafeDiagnosingMatcher<I> {
    private final Matcher<?> elementMatcher;

    public IsCollectionContaining(Matcher<?> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }

    @Override
    protected boolean matchesSafely(I collection, Description mismatchDescription) {
        boolean empty = true;
        for (Object item : collection) {
            empty = false;
            if (elementMatcher.matches(item)) {
                return true;
            }
        }

        if (empty) {
            mismatchDescription.appendText("was an empty collection");
            return false;
        }

        mismatchDescription.appendText("was a collection that did not contain ")
                           .appendDescriptionOf(elementMatcher)
                           .appendText(" -- mismatches were: [");
        boolean isPastFirst = false;
        for (Object item : collection) {
            if (isPastFirst) {
              mismatchDescription.appendText(", ");
            }
            elementMatcher.describeMismatch(item, mismatchDescription);
            isPastFirst = true;
        }
        mismatchDescription.appendText("]");
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description
            .appendText("a collection containing ")
            .appendDescriptionOf(elementMatcher);
    }


    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields at least one item that is matched by the specified
     * <code>itemMatcher</code>.  Whilst matching, the traversal of the examined {@link Iterable}
     * will stop as soon as a matching item is found.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem(startsWith("ba")))</pre>
     *
     * @param itemMatcher
     *     the matcher to apply to items provided by the examined {@link Iterable}
     */
    @Factory
    public static <I extends Iterable<?>> Matcher<I> hasItem(Matcher<?> itemMatcher) {
        return new IsCollectionContaining<I>(itemMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields at least one item that is equal to the specified
     * <code>item</code>.  Whilst matching, the traversal of the examined {@link Iterable}
     * will stop as soon as a matching item is found.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem("bar"))</pre>
     *
     * @param item
     *     the item to compare against the items provided by the examined {@link Iterable}
     */
    @Factory
    public static <I extends Iterable<?>> Matcher<I> hasItem(Object item) {
        return hasItem(equalTo(item));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
     * examined {@link Iterable} yield at least one item that is matched by the corresponding
     * matcher from the specified <code>itemMatchers</code>.  Whilst matching, each traversal of
     * the examined {@link Iterable} will stop as soon as a matching item is found.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems(endsWith("z"), endsWith("o")))</pre>
     *
     * @param itemMatchers
     *     the matchers to apply to items provided by the examined {@link Iterable}
     */
    @Factory
    public static <I extends Iterable<?>> Matcher<I> hasItems(Matcher<?>... itemMatchers) {
        List<Matcher<? super I>> all = new ArrayList<Matcher<? super I>>(itemMatchers.length);

        for (Matcher<?> elementMatcher : itemMatchers) {
          all.add(hasItem(elementMatcher));
        }

        return allOf(all);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
     * examined {@link Iterable} yield at least one item that is equal to the corresponding
     * item from the specified <code>items</code>.  Whilst matching, each traversal of the
     * examined {@link Iterable} will stop as soon as a matching item is found.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems("baz", "foo"))</pre>
     *
     * @param items
     *     the items to compare against the items provided by the examined {@link Iterable}
     */
    @Factory
    public static <I extends Iterable<?>> Matcher<I> hasItems(Object... items) {
        List<Matcher<? super I>> all = new ArrayList<Matcher<? super I>>(items.length);
        for (Object element : items) {
            all.add(hasItem(element));
        }

        return allOf(all);
    }

}
