/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.beans;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;

import java.beans.IntrospectionException;

/**
 * Matcher that checks that an object has a JavaBean property
 * with the specified name. If an error occurs while introspecting
 * the object then this is treated as a matcher failure.
 *
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 * @since 1.1.0
 */
public class HasProperty<T> implements Matcher<T> {

    private final String propertyName;

    public HasProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    public boolean match(T obj) {
        try {
            return PropertyUtil.getPropertyDescriptor(propertyName, obj) != null;
        } catch (IntrospectionException e) {
            // introspection failure is treated as a matcher failure
            return false;
        }
    }

    public void describeTo(Description description) {
        description.appendText("hasProperty(").appendValue(propertyName).appendText(")");
    }

    @Factory
    public static <T> Matcher<T> hasProperty(String propertyName) {
        return new HasProperty<T>(propertyName);
    }

}
