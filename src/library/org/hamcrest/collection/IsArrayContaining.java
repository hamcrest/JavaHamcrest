package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import static org.hamcrest.core.IsEqual.eq;

import java.lang.reflect.Array;

public class IsArrayContaining<T> implements Matcher<T[]> {

    private final Matcher<T> elementMatcher;

    public IsArrayContaining(Matcher<T> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }

    @SuppressWarnings({"unchecked"})
    public boolean match(T[] array) {
        if (array == null) {
            return false;
        }
        int size = Array.getLength(array);
        for (int i = 0; i < size; i++) {
            if (elementMatcher.match((T)Array.get(array, i))) {
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
