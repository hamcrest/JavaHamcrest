package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

public abstract class DiagnosingIterableMatcher<T> extends DiagnosingMatcher<Iterable<T>> {
    @SuppressWarnings("unchecked")
    protected boolean matches(Object item, Description mismatchDescription) {
        boolean result = false;
        if (item == null) {
            mismatchDescription.appendText("Item was null.");
        } else if (!(item instanceof Iterable)) {
            mismatchDescription.appendText("Item was not an instance of " + Iterable.class);
        } else {
            result = matchesSafely((Iterable<T>)item, mismatchDescription);
        }
        return result;
    }

    protected abstract boolean matchesSafely(Iterable<T> iterable, Description mismatchDescription);
}
