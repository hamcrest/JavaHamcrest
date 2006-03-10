package org.hamcrest.collection;

import org.hamcrest.Matcher;
import org.hamcrest.Description;

import java.lang.reflect.Array;

public class IsArrayContaining implements Matcher {
    private final Matcher elementMatcher;

    public IsArrayContaining(Matcher elementMatcher) {
        this.elementMatcher = elementMatcher;
    }

    public boolean match(Object o) {
        return o != null
                && o.getClass().isArray()
                && arrayContainsMatchingElement(o);
    }

    private boolean arrayContainsMatchingElement(Object array) {
        int size = Array.getLength(array);
        for (int i = 0; i < size; i++) {
            if (elementMatcher.match(Array.get(array, i))) return true;
        }
        return false;
    }

    public void describeTo(Description description) {
        description.append("an array containing ");
        elementMatcher.describeTo(description);
    }
}
