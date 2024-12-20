package org.hamcrest.beans;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher that checks if an object has a JavaBean property with the
 * specified name. If an error occurs during introspection of the object
 * then this is treated as a mismatch.
 *
 * @param <T> The Matcher type.
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 */
public class HasProperty<T> extends TypeSafeMatcher<T> {

    private final String propertyName;

    /**
     * Constructor, best called from {@link #hasProperty(String)}.
     * @param propertyName the name of the property
     * @see #hasProperty(String) 
     */
    public HasProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public boolean matchesSafely(T obj) {
        try {
            return PropertyUtil.getPropertyDescriptor(propertyName, obj) != null ||
                    PropertyUtil.getMethodDescriptor(propertyName, obj) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("no ").appendValue(propertyName).appendText(" in ").appendValue(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("hasProperty(").appendValue(propertyName).appendText(")");
    }

    /**
     * Creates a matcher that matches when the examined object has a JavaBean property
     * with the specified name.
     * For example:
     * <pre>assertThat(myBean, hasProperty("foo"))</pre>
     *
     * @param <T>
     *     the matcher type.
     * @param propertyName
     *     the name of the JavaBean property that examined beans should possess
     * @return The matcher.
     */
    public static <T> Matcher<T> hasProperty(String propertyName) {
        return new HasProperty<>(propertyName);
    }

}
