package org.hamcrest;

import java.lang.reflect.Method;

/**
 * TODO(ngd): Document
 *
 * @param <T>
 */
public abstract class TypeSafeDiagnosingMatcher<T> extends BaseMatcher<T> {
    private final Class<?> expectedType;

    protected TypeSafeDiagnosingMatcher() {
        expectedType = findExpectedType(getClass());
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

    /**
     * Subclasses should implement this. The item will already have been checked
     * for the specific type and will never be null.
     */
    public abstract boolean matchesSafely(T item, Description mismatchDescription);

    @SuppressWarnings("unchecked")
    public final boolean matches(Object item) {
        return item != null
            && expectedType.isInstance(item)
            && matchesSafely((T) item, new Description.NullDescription());
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void describeMismatch(Object item, Description mismatchDescription) {
        matchesSafely((T) item, mismatchDescription);
    }
}
