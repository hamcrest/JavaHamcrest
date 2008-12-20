/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.beans;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher that checks that an object has a JavaBean property
 * with the specified name. If an error occurs while introspecting
 * the object then this is treated as a matcher failure.
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
    	// TODO(ngd): this is not type safe.
        try {
            return PropertyUtil.getPropertyDescriptor(propertyName, obj) != null;
        } catch (IllegalArgumentException e) {
            // introspection failure is treated as a matcher failure
            return false;
        }
    }
    
    @Override
    public void describeMismatchSafely(T item, Description mismatchDescription) {
      mismatchDescription.appendText("no ").appendValue(propertyName).appendText(" in ").appendValue(item);
    };

    public void describeTo(Description description) {
        description.appendText("hasProperty(").appendValue(propertyName).appendText(")");
    }

    @Factory
    public static <T> Matcher<T> hasProperty(String propertyName) {
        return new HasProperty<T>(propertyName);
    }

}
