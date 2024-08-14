package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher that applies a delegate matcher to every item in an  {@link Iterable}.
 *
 * @param <T> the type of the items in the iterable
 */
public class Every<T> extends TypeSafeDiagnosingMatcher<Iterable<? extends T>> {

    private final Matcher<? super T> matcher;

    /**
     * Constructor, best called from {@link #everyItem(Matcher)}.
     * @param matcher a matcher used to check every item in the iterable.
     */
    public Every(Matcher<? super T> matcher) {
        this.matcher= matcher;
    }

    @Override
    public boolean matchesSafely(Iterable<? extends T> collection, Description mismatchDescription) {
        for (T t : collection) {
            if (!matcher.matches(t)) {
                mismatchDescription.appendText("an item ");
                matcher.describeMismatch(t, mismatchDescription);
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("every item is ").appendDescriptionOf(matcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields items that are all matched by the specified
     * <code>itemMatcher</code>.
     * For example:
     * <pre>assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")))</pre>
     *
     * @param <U>
     *     the matcher type.
     * @param itemMatcher
     *     the matcher to apply to every item provided by the examined {@link Iterable}
     * @return The matcher.
     */
    public static <U> Matcher<Iterable<? extends U>> everyItem(final Matcher<U> itemMatcher) {
        return new Every<>(itemMatcher);
    }

}
