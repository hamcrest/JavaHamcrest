package org.hamcrest;

/**
 * Convenient base class for Matchers of a specific type and that will report why the
 * received value has been rejected.
 *
 * Unlike the {@link TypeSafeDiagnosingMatcher}, this does not implement the null check
 * or validate the type, so subclasses need to be prepared to handle these conditions.
 *
 * To use, implement {@link #matches(Object, Description)}
 *
 * @param <T> the type of matcher being diagnosed.
 * @see TypeSafeDiagnosingMatcher
 */
public abstract class DiagnosingMatcher<T> extends BaseMatcher<T> {

    /**
     * Constructor
     */
    public DiagnosingMatcher() {
    }

    @Override
    public final boolean matches(Object item) {
        return matches(item, Description.NONE);
    }

    @Override
    public final void describeMismatch(Object item, Description mismatchDescription) {
        matches(item, mismatchDescription);
    }

    /**
     * Evaluates the matcher for argument <var>item</var>.
     * @param item the value to check
     * @param mismatchDescription the description for the matcher
     * @return <code>true</code> if <var>item</var> matches, otherwise <code>false</code>.
     */
    protected abstract boolean matches(Object item, Description mismatchDescription);

}
