package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.core.IsEqual.eq;

import java.lang.reflect.Array;

public class IsArrayContaining<T> extends TypeSafeMatcher<T[]> {

    private final Matcher<T> elementMatcher;

    public IsArrayContaining(Matcher<T> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }

    public boolean matchSafely(T[] array) {
        int size = Array.getLength(array);
        for (int i = 0; i < size; i++) {
            if (elementMatcher.match(Array.get(array, i))) {
                return true;
            }
        }
        return false;
    }

    public void describeTo(Description description) {
        description.appendText("an array containing ");
        elementMatcher.describeTo(description);
    }

    @Factory
    public static <T> Matcher<T[]> arrayContaining(Matcher<T> elementMatcher) {
        return new IsArrayContaining<T>(elementMatcher);
    }

    @Factory
    public static <T> Matcher<T[]> arrayContaining(T element) {
        return arrayContaining(eq(element));
    }

}
