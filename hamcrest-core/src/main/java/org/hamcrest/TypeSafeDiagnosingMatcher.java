package org.hamcrest;

import org.hamcrest.internal.ReflectiveTypeFinder;


/**
 * Convenient base class for Matchers that require a non-null value of a specific type
 * and that will report why the received value has been rejected.
 * This implements the null check, checks the type and then casts. 
 * To use, implement <pre>matchesSafely()</pre>. 
 *
 * @param <T>
 * @author Neil Dunn
 * @author Nat Pryce
 * @author Steve Freeman
 */
public abstract class TypeSafeDiagnosingMatcher<T> extends BaseMatcher<T> {
    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("matchesSafely", 2, 0); 
    private final Class<?> expectedType;

    /**
     * Subclasses should implement this. The item will already have been checked
     * for the specific type and will never be null.
     */
    protected abstract boolean matchesSafely(T item, Description mismatchDescription);

    /**
     * Use this constructor if the subclass that implements <code>matchesSafely</code> 
     * is <em>not</em> the class that binds &lt;T&gt; to a type. 
     * @param expectedType The expectedType of the actual value.
     */
    protected TypeSafeDiagnosingMatcher(Class<?> expectedType) {
      this.expectedType = expectedType;
    }

    /**
     * Use this constructor if the subclass that implements <code>matchesSafely</code> 
     * is <em>not</em> the class that binds &lt;T&gt; to a type. 
     * @param typeFinder A type finder to extract the type
     */
    protected TypeSafeDiagnosingMatcher(ReflectiveTypeFinder typeFinder) {
      this.expectedType = typeFinder.findExpectedType(getClass()); 
    }

    /**
     * The default constructor for simple sub types
     */
    protected TypeSafeDiagnosingMatcher() {
      this(TYPE_FINDER); 
    }

    @Override
    @SuppressWarnings("unchecked")
    public final boolean matches(Object item) {
        return item != null
            && expectedType.isInstance(item)
            && matchesSafely((T) item, new Description.NullDescription());
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void describeMismatch(Object item, Description mismatchDescription) {
      if (item == null || !expectedType.isInstance(item)) {
        super.describeMismatch(item, mismatchDescription);
      } else {
        matchesSafely((T) item, mismatchDescription);
      }
    }
}
