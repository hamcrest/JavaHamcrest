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
 * @author Nat Pryce
 */
public abstract class TypeSafeDiagnosingMatcher<T> extends BaseMatcher<T> {
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
    public final boolean matches(Object item) {
        return item != null
            && expectedType.isInstance(item)
            && matchesSafely((T) item, new Description.NullDescription());
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void describeMismatch(Object item, Description mismatchDescription) {
        matchesSafely((T) item, mismatchDescription);
    }
    
    private Class<?> findExpectedType(Class<?> fromClass) {
        for (Class<?> c = fromClass; c != Object.class; c = c.getSuperclass()) {
            for (Method method : c.getDeclaredMethods()) {
                if (canObtainExpectedTypeFrom(method)) {
                    return obtainExpectedTypeFrom(method);
                }
            }
        }
        
        throw new Error("Cannot determine correct type for matchesSafely() method.");
    }
    
    protected boolean canObtainExpectedTypeFrom(Method method) {
        return method.getName().equals("matchesSafely")
                && method.getParameterTypes().length == 2
                && !method.isSynthetic();
    }
    
    protected Class<?> obtainExpectedTypeFrom(Method method) {
        return method.getParameterTypes()[0];
    }
}
