package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher of {@link Class} that matches when the specified baseType is assignable from the examined class.
 * @param <T> the type of the class
 */
public class IsCompatibleType<T> extends TypeSafeMatcher<Class<?>> {

    private final Class<T> type;

    /**
     * Constructor, best called from {@link #typeCompatibleWith(Class)}.
     * @param type the expected type
     */
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
     * For example:
     * <pre>assertThat(Integer.class, typeCompatibleWith(Number.class))</pre>
     *
     * @param <T>
     *     the matcher type.
     * @param baseType
     *     the base class to examine classes against
     * @return The matcher.
     */
    public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> baseType) {
        return new IsCompatibleType<>(baseType);
    }

}
