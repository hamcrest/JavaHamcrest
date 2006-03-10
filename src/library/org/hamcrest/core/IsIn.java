package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

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

    public void describeTo(Description description) {
        description.appendText("one of {");
        boolean seenFirst = false;
        for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
            Object item = iterator.next();
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
