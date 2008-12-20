package org.hamcrest.collection;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matches if an array contains an item satisfying a nested matcher.
 */
public class IsArrayContaining<T> extends TypeSafeMatcher<T[]> {
    private final Matcher<? super T> elementMatcher;

    public IsArrayContaining(Matcher<? super T> elementMatcher) {
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
    
    @Override
    public void describeMismatchSafely(T[] item, Description mismatchDescription) {
      super.describeMismatch(Arrays.asList(item), mismatchDescription);
    };

    public void describeTo(Description description) {
        description
            .appendText("an array containing ")
            .appendDescriptionOf(elementMatcher);
    }

    /**
     * Evaluates to true if any item in an array satisfies the given matcher.
     */
    @Factory
    public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> elementMatcher) {
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
        Matcher<? super T> matcher = equalTo(element);
        return IsArrayContaining.<T>hasItemInArray(matcher);
    }
}
