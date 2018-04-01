package org.hamcrest;

/**
 * TODO(ngd): Document.
 *
 * @param <T>
 */
public abstract class DiagnosingMatcher<T> extends BaseMatcher<T> {

    @Override
    public final boolean matches(T item) {
        return matches(item, Description.NONE);
    }

    @Override
    public final void describeMismatch(T item, Description mismatchDescription) {
        matches(item, mismatchDescription);
    }

    protected abstract boolean matches(T item, Description mismatchDescription);
}
