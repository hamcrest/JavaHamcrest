/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.beans;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A Matcher that checks that an object has a JavaBean property
 * with the specified name. If an error occurs during introspection
 * of the object then this is treated as a mismatch.
 *
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 */
public class HasProperty<T> extends TypeSafeMatcher<T> {

    private final String propertyName;

    public HasProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public boolean matchesSafely(T obj) {
        try {
            return PropertyUtil.getPropertyDescriptor(propertyName, obj) != null;
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
     * <p/>
     * For example:
     * <pre>assertThat(myBean, hasProperty("foo"))</pre>
     * 
     * @param propertyName
     *     the name of the JavaBean property that examined beans should possess
     */
    @Factory
    public static <T> Matcher<T> hasProperty(String propertyName) {
        return new HasProperty<T>(propertyName);
    }

}
