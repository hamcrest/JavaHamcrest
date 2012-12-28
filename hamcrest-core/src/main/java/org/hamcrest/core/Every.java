package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class Every<I extends Iterable<?>> extends TypeSafeDiagnosingMatcher<I> {
    private final Matcher<?> matcher;

    public Every(Matcher<?> matcher) {
        this.matcher= matcher;
    }

    @Override
    public boolean matchesSafely(I collection, Description mismatchDescription) {
        for (Object o : collection) {
            if (!matcher.matches(o)) {
                mismatchDescription.appendText("an item ");
                matcher.describeMismatch(o, mismatchDescription);
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
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")))</pre>
     *
     * @param itemMatcher
     *     the matcher to apply to every item provided by the examined {@link Iterable}
     */
    @Factory
    public static <I extends Iterable<?>> Matcher<I> everyItem(final Matcher<?> itemMatcher) {
        return new Every<I>(itemMatcher);
    }
}
