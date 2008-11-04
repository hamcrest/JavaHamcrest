package org.hamcrest.number;

import org.hamcrest.BaseMatcher;

public abstract class ComparableMatcher<T extends Comparable<T>> extends BaseMatcher<T> {
    @SuppressWarnings("unchecked")
    public boolean matches(Object item) {
        return item instanceof Comparable && matchesSafely((T)item);
    }
    
    protected abstract boolean matchesSafely(T comparable);
}
