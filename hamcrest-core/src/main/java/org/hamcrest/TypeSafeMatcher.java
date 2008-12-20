package org.hamcrest;

import org.hamcrest.internal.ReflectiveTypeFinder;

/**
 * Convenient base class for Matchers that require a non-null value of a specific type.
 * This simply implements the null check, checks the type and then casts.
 *
 * @author Joe Walnes
 */
public abstract class TypeSafeMatcher<T> extends BaseMatcher<T> {
    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("matchesSafely", 1, 0); 
    final private Class<?> expectedType;

    /**
     * Subclasses should implement these. The item will already have been checked for
     * the specific type and will never be null.
     */
    protected abstract boolean matchesSafely(T item);
    protected abstract void describeMismatchSafely(T item, Description mismatchDescription);
    
    /**
     * The default constructor for simple sub types
     */
    protected TypeSafeMatcher() {
        this(TYPE_FINDER);
    }
    
   
    /**
     * Use this constructor if the subclass that implements <code>matchesSafely</code> 
     * is <em>not</em> the class that binds &lt;T&gt; to a type. 
     * @param expectedType The expectedType of the actual value.
     */
    protected TypeSafeMatcher(Class<?> expectedType) {
        this.expectedType = expectedType;
    }

    
    /**
     * Use this constructor if the subclass that implements <code>matchesSafely</code> 
     * is <em>not</em> the class that binds &lt;T&gt; to a type. 
     * @param typeFinder A type finder to extract the type
     */
    protected TypeSafeMatcher(ReflectiveTypeFinder typeFinder) {
      this.expectedType = typeFinder.findExpectedType(getClass()); 
    }
 
    /**
     * Methods made final to prevent accidental override.
     * If you need to override this, there's no point on extending TypeSafeMatcher.
     * Instead, extend the {@link BaseMatcher}.
     */
    @SuppressWarnings({"unchecked"})
    public final boolean matches(Object item) {
        return item != null
                && expectedType.isInstance(item)
                && matchesSafely((T) item);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    final public void describeMismatch(Object item, Description description) {
      if (item == null || ! expectedType.isInstance(item)) {
        super.describeMismatch(item, description);
      } else {
        describeMismatchSafely((T)item, description);
      }
    }
}
