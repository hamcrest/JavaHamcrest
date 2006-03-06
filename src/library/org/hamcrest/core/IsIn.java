package org.hamcrest.core;

import org.hamcrest.Formatting;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.Collection;

public class IsIn implements Matcher {
    private final Collection collection;

    public IsIn(Collection collection) {
        this.collection = collection;
    }

    public IsIn(Object[] elements) {
        collection = Arrays.asList(elements);
    }

    public boolean match(Object o) {
        return collection.contains(o);
    }

    public void describeTo(StringBuffer buffer) {
        buffer.append("one of ");
        Formatting.join(collection, buffer, "{", "}");
    }
}
