package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

public class IsCompatibleType<T> extends TypeSafeMatcher<Class<?>> {
    private final Class<T> type;
    
    public IsCompatibleType(Class<T> type) {
        this.type = type;
    }
    
    @Override
    public boolean matchesSafely(Class<?> cls) {
        return type.isAssignableFrom(cls);
    }
    
    @Override
    public void describeMismatchSafely(Class<?> cls, Description mismatchDescription) {
      mismatchDescription.appendValue(cls.getName());
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("type < ").appendText(type.getName());
    }
    
    /**
     * Creates a matcher of {@link Class} that matches when the specified baseType is
     * assignable from the examined class.
     * <p/>
     * For example:
     * <pre>assertThat(Integer.class, typeCompatibleWith(Number.class))</pre>
     * 
     * @param baseType
     *     the base class to examine classes against
     */
    @Factory
    public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> baseType) {
        return new IsCompatibleType<T>(baseType);
    }
}
