package org.hamcrest;

import java.lang.reflect.Method;

/**
 * Convenient base class for Matchers that require a non-null value of a specific type.
 * This simply implements the null check, checks the type and then casts.
 *
 * @author Joe Walnes
 */
public abstract class TypeSafeMatcher<T> extends BaseMatcher<T> {

    private Class expectedType;

    /**
     * Subclasses should implement this. The item will already have been checked for
     * the specific type and will never be null.
     */
    protected abstract boolean matchesSafely(T item);

    protected TypeSafeMatcher() {
        // Determine what T actually is.
        for (Method method : getClass().getMethods()) {
            if (method.getName().equals("matchesSafely") && !method.isSynthetic()) {
                expectedType = method.getParameterTypes()[0];
                break;
            }
        }
        if (expectedType == null) {
            throw new Error("Cannot determine correct type for matchesSafely() method.");
        }
    }
    
    protected TypeSafeMatcher(Class<T> expectedType) {
    	this.expectedType = expectedType;
    }

    /**
     * Method made final to prevent accidental override.
     * If you need to override this, there's no point on extending TypeSafeMatcher.
     * Instead, implement the Matcher interface directly.
     */
    @SuppressWarnings({"unchecked"})
    public final boolean matches(Object item) {
        return item != null
                && expectedType.isInstance(item)
                && matchesSafely((T) item);
    }

}
