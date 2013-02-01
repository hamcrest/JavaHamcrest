package org.hamcrest;

import org.hamcrest.internal.ReflectiveTypeFinder;

/**
 * Convenient base class for Matchers that require a non-null value of a specific type.
 * This simply implements the null check, checks the type and then casts. See also
 * {@link TypeSafeDiagnosingMatcher}.
 *
 * @author Joe Walnes
 * @author Steve Freeman
 * @author Nat Pryce
 */
public abstract class TypeSafeMatcher<T> extends BaseMatcher<T> {
    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("matchesSafely", 1, 0);
    
    final private Class<?> expectedType;

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
     * Subclasses should implement this. The item will already have been checked for
     * the specific type and will never be null.
     */
    protected abstract boolean matchesSafely(T item);
    
    /**
     * Subclasses should override this. The item will already have been checked for
     * the specific type and will never be null.
     * The description should be able to replace Y in the sentence "Expected X but Y," and
     * should be in the past tense, for example, "Expected null but <U>was &lt;"foo"&gt;</U>."
     * The description should NOT describe the matcher, but rather should highlight features of interest on the item as they actually are.
     * The description must ALWAYS be filled in, regardless of return value of {@link #matchesSafely}. 
     */
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        super.describeMismatch(item, mismatchDescription);
    }
    
    /**
     * Methods made final to prevent accidental override.
     * If you need to override this, there's no point on extending TypeSafeMatcher.
     * Instead, extend the {@link BaseMatcher}.
     */
    @Override
    @SuppressWarnings({"unchecked"})
    public final boolean matches(Object item) {
        return item != null
                && expectedType.isInstance(item)
                && matchesSafely((T) item);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    final public void describeMismatch(Object item, Description description) {
        if (item == null) {
            super.describeMismatch(item, description);
        } else if (! expectedType.isInstance(item)) {
            description.appendText("was a ")
                       .appendText(item.getClass().getName())
                       .appendText(" (")
                       .appendValue(item)
                       .appendText(")");
        } else {
            describeMismatchSafely((T)item, description);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<T> getParameterType() {
        return (Class<T>)expectedType;
    }
}
