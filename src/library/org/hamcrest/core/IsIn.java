package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;
import java.util.Collection;

public class IsIn<T> extends TypeSafeMatcher<T> {
    private final Collection<T> collection;

    public IsIn(Collection<T> collection) {
        this.collection = collection;
    }

    public boolean matchesSafely(T item) {
        return collection.contains(item);
    }

    public void describeTo(Description description) {
        description.appendText("one of {");
        boolean seenFirst = false;
        for (T item : collection) {
            if (seenFirst) {
                description.appendText(", ");
            } else {
                seenFirst = true;
            }
            description.appendValue(item);
        }
        description.appendText("}");
    }

    @Factory 
    public static <T> Matcher<T> isInCollection(Collection<T> elements) {
        return new IsIn<T>(elements);
    }

    @Factory
    public static <T> Matcher<T> isIn(T... elements) {
        return isInCollection(Arrays.asList(elements));
    }

}
