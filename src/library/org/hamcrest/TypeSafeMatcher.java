package org.hamcrest;

/**
 * Convenient base class for Matchers that require a non-null value of a specific type.
 * This simply implements the null check, checks the type and then casts.
 *
 * @author Joe Walnes
 */
public abstract class TypeSafeMatcher<T> implements Matcher<T> {

    /**
     * Subclasses should implement this. The item will already have been checked for
     * the specific type and will never be null.
     */
    protected abstract boolean matchesSafely(T item);

    /**
     * Method made final to prevent accidental override.
     * If you need to override this, there's no point on extending TypeSafeMatcher.
     * Instead, implement the Matcher interface directly.
     */
    @SuppressWarnings({"unchecked"})
    public final boolean matches(Object item) {
        return item != null && matchesSafely((T) item);
    }
    
}
