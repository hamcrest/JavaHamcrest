package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matches if an array contains an item satisfying a nested matcher.
 */
public class IsArrayContaining<T> extends ArrayMatcher<T> {
    private final Matcher<T> elementMatcher;

    public IsArrayContaining(Matcher<T> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }

    @Override
    public boolean matchesSafely(T[] array) {
        for (T item : array) {
            if (elementMatcher.matches(item)) {
                return true;
            }
        }
        return false;
    }

    public void describeTo(Description description) {
        description
            .appendText("an array containing ")
            .appendDescriptionOf(elementMatcher);
    }

    /**
     * Evaluates to true if any item in an array satisfies the given matcher.
     */
    @Factory
    public static <T> Matcher<T[]> hasItemInArray(Matcher<T> elementMatcher) {
        return new IsArrayContaining<T>(elementMatcher);
    }

    /**
     * This is a shortcut to the frequently used hasItemInArray(equalTo(x)).
     *
     * For example,  assertThat(hasItemInArray(equal_to(x)))
     *          vs.  assertThat(hasItemInArray(x))
     */
    @Factory
    public static <T> Matcher<T[]> hasItemInArray(T element) {
        return hasItemInArray(equalTo(element));
    }
}
