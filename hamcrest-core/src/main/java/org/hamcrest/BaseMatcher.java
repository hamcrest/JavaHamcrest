package org.hamcrest;

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

    @Override
    public void describeMismatch(T item, Description description) {
        description.appendText("was ").appendValue(item);
    }

    @Override
    public String toString() {
        return StringDescription.toString(this);
    }

    /**
     * Useful null-check method. Writes a mismatch description if the actual object is null
     * @param actual the object to check
     * @param mismatch where to write the mismatch description, if any
     * @return false iff the actual object is null
     */
    protected static boolean isNotNull(Object actual, Description mismatch) {
        if (actual == null) {
            mismatch.appendText("was null");
            return false;
        }
        return true;
    }
}
