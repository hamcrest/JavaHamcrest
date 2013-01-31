package org.hamcrest;

/**
 * This is a base class for matchers that use a single method to
 * both detect a match and describe a mismatch (in contrast to
 * {@link BaseMatcher}, which has separate methods with potentially
 * duplicate logic).
 */
public abstract class DiagnosingMatcher<T> extends BaseMatcher<T> {

    @Override
    public final boolean matches(Object item) {
        return matches(item, Description.NONE);
    }

    @Override
    public final void describeMismatch(Object item, Description mismatchDescription) {
        matches(item, mismatchDescription);
    }

    protected abstract boolean matches(Object item, Description mismatchDescription);
}
