package org.hamcrest;

import org.hamcrest.core.AllOf;

/**
 * BaseClass for all Matcher implementations.
 *
 * @see Matcher
 */
public abstract class BaseMatcher<T> implements Matcher<T> {

    /**
     * @see Matcher#_dont_implement_Matcher___instead_extend_BaseMatcher_()
     */
    @Override
    @Deprecated
    public final void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
        // See Matcher interface for an explanation of this method.
    }

    /**
     * @see AllOf#allOf(Matcher[])
     */
    @Override
    public Matcher<T> and(Matcher<? super T> otherMatcher) {
        return AllOf.<T>allOf(this, otherMatcher);
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("was ").appendValue(item);
    }

    @Override
    public String toString() {
        return StringDescription.toString(this);
    }
}
