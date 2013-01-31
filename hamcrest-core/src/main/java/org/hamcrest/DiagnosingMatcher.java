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

    /**
     * The description should be able to replace Y in the sentence "Expected X but Y," and
     * should be in the past tense, for example, "Expected null but <U>was &lt;"foo"&gt;</U>."
     * The description should NOT describe the matcher, but rather should highlight features of interest on the item as they actually are.
     */
    protected abstract boolean matches(Object item, Description mismatchDescription);
}
