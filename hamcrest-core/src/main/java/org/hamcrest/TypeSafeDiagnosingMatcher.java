package org.hamcrest;

import java.lang.reflect.Method;

/**
 * Convenient base class for Matchers that require a non-null value of a specific type
 * and that will report why the received value has been rejected.
 * This implements the null check, checks the type and then casts. 
 * To use, implement <pre>matchesSafely()</pre>. 
 *
 * @param <T>
 * @author Neil Dunn
 */
public abstract class TypeSafeDiagnosingMatcher<T> extends DiagnosingMatcher<T> {
    private final Class<?> expectedType;

    protected TypeSafeDiagnosingMatcher() {
      this.expectedType = findExpectedType(getClass());
    }

    /**
     * Subclasses should implement this. The item will already have been checked
     * for the specific type and will never be null.
     */
    public abstract boolean matchesSafely(T item, Description mismatchDescription);

    @SuppressWarnings("unchecked")
    protected boolean matches(Object item, Description mismatchDescription) {
        boolean result = false;
        if (item == null) {
            mismatchDescription.appendText("The item was null.");
        } else if (!expectedType.isInstance(item)) {
            mismatchDescription.appendText("The item was not an instance of " + expectedType);
        } else {
            result = matchesSafely((T) item, mismatchDescription);
        }
        return result;
    }

    private static Class<?> findExpectedType(Class<?> fromClass) {
        for (Class<?> c = fromClass; c != Object.class; c = c.getSuperclass()) {
            for (Method method : c.getDeclaredMethods()) {
                if (isMatchesSafelyMethod(method)) {
                    return method.getParameterTypes()[0];
                }
            }
        }

        throw new Error("Cannot determine correct type for matchesSafely() method.");
    }

    private static boolean isMatchesSafelyMethod(Method method) {
        return method.getName().equals("matchesSafely")
                && method.getParameterTypes().length == 2
                && !method.isSynthetic();
    }

}
