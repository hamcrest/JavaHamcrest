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
    public void describeMismatch(Object item, Description description) {
        description.appendText("was ").appendValue(item);
    }

    /**
     * @return true if the given object is a BaseMatcher
     * and its String representation is equal to this one.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof BaseMatcher)) {
            return false;
        }
        return toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return StringDescription.toString(this);
    }
}
