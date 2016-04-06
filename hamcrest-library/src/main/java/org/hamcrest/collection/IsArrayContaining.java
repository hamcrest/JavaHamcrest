package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;

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
    }

    @Override
    public void describeTo(Description description) {
        description
            .appendText("an array containing ")
            .appendDescriptionOf(elementMatcher);
    }

}
