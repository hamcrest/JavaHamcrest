package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsArrayContaining<T> extends TypeSafeMatcher<T[]> {

    private final Matcher<T> elementMatcher;

    public IsArrayContaining(Matcher<T> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }

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

    @Factory
    public static <T> Matcher<T[]> hasItemInArray(Matcher<T> elementMatcher) {
        return new IsArrayContaining<T>(elementMatcher);
    }

    @Factory
    public static <T> Matcher<T[]> hasItemInArray(T element) {
        return hasItemInArray(equalTo(element));
    }

}
