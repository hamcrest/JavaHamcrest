package org.hamcrest.collection;

import org.hamcrest.BaseMatcher;

public abstract class ArrayMatcher<T> extends BaseMatcher<T[]> {
    @SuppressWarnings("unchecked")
    public boolean matches(Object item) {
        if (!(item instanceof Object[])) {
            return false;
        }
        return matchesSafely((T[])item);
    }
    
    protected abstract boolean matchesSafely(T[] array);
}
