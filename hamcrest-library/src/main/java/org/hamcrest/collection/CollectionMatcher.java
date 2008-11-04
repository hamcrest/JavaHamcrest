package org.hamcrest.collection;

import org.hamcrest.BaseMatcher;

import java.util.Collection;

public abstract class CollectionMatcher<T> extends BaseMatcher<Collection<T>> {
    @SuppressWarnings("unchecked")
    public boolean matches(Object item) {
        if (!(item instanceof Collection)) {
            return false;
        }
        return matchesSafely((Collection<T>)item);
    }
    
    protected abstract boolean matchesSafely(Collection<T> collection);
}
