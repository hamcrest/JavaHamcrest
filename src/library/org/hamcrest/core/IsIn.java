package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.Collection;

public class IsIn<T> implements Matcher<T> {
    private final Collection<T> collection;

    public IsIn(Collection<T> collection) {
        this.collection = collection;
    }

    public IsIn(T... elements) {
        collection = Arrays.asList(elements);
    }

    public boolean match(T o) {
        return collection.contains(o);
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
}
