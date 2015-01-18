package org.hamcrest.core;

import org.hamcrest.*;
import org.hamcrest.internal.SelfDescribingText;

import java.util.ArrayList;
import java.util.List;

public class Every<T> extends TypeSafeDiagnosingMatcher<Iterable<? extends T>> {
    private final Matcher<? super T> matcher;

    public Every(Matcher<? super T> matcher) {
        this.matcher= matcher;
    }

    @Override
    public boolean matchesSafely(Iterable<? extends T> collection, Description mismatchDescription) {
        List<SelfDescribingText> mismatches = new ArrayList<SelfDescribingText>();
        for (T t : collection) {
            if (!matcher.matches(t)) {
                mismatches.add(mismatchString(t));
            }
        }

        switch (mismatches.size()) {
            case 0:
                return true;

            case 1:
                mismatchDescription.appendText("an item ");
                mismatchDescription.appendDescriptionOf(mismatches.get(0));
                return false;

            default:
                mismatchDescription
                        .appendText("a collection with ")
                        .appendValue(mismatches.size())
                        .appendText(" mismatches: ")
                        .appendList("[", ", ", "]", mismatches);
                return false;
        }
    }

    private SelfDescribingText mismatchString(T t) {
        Description desc = new StringDescription();
        matcher.describeMismatch(t, desc);
        return new SelfDescribingText(desc.toString());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a collection in which every item is ").appendDescriptionOf(matcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields items that are all matched by the specified
     * <code>itemMatcher</code>.
     * For example:
     * <pre>assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")))</pre>
     *
     * <p>
     *     Note that the matcher will diagnose <em>all</em> mismatching items, rather than stopping at the first.
     * </p>
     * 
     * @param itemMatcher
     *     the matcher to apply to every item provided by the examined {@link Iterable}
     */
    @Factory
    public static <U> Matcher<Iterable<? extends U>> everyItem(final Matcher<U> itemMatcher) {
        return new Every<U>(itemMatcher);
    }
}
