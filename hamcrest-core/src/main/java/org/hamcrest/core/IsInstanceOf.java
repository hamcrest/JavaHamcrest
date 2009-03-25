/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;


/**
 * Tests whether the value is an instance of a class.
 */
public class IsInstanceOf extends DiagnosingMatcher<Object> {
    private final Class<?> theClass;

    /**
     * Creates a new instance of IsInstanceOf
     *
     * @param theClass The predicate evaluates to true for instances of this class
     *                 or one of its subclasses.
     */
    public IsInstanceOf(Class<?> theClass) {
        this.theClass = theClass;
    }

    @Override
    protected boolean matches(Object item, Description mismatchDescription) {
      if (null == item) {
        mismatchDescription.appendText("null");
        return false;
      }
      
      if (!theClass.isInstance(item)) {
        mismatchDescription.appendValue(item).appendText(" is a " + item.getClass().getName());
        return false;
      }
      
      return true;
    }

    public void describeTo(Description description) {
        description.appendText("an instance of ")
                .appendText(theClass.getName());
    }

    /**
     * Is the value an instance of a particular type? 
     * This version assumes no relationship between the required type and
     * the signature of the method that sets it up, for example in
     * <code>assertThat(anObject, instanceOf(Thing.class));</code>
     */
    @SuppressWarnings("unchecked")
    @Factory
    public static <T> Matcher<T> instanceOf(Class<?> type) {
        return (Matcher<T>) new IsInstanceOf(type);
    }
    
    /**
     * Is the value an instance of a particular type? 
     * Use this version to make generics conform, for example in 
     * the JMock clause <code>with(any(Thing.class))</code> 
     */
    @SuppressWarnings("unchecked")
    @Factory
    public static <T> Matcher<T> any(Class<T> type) {
        return (Matcher<T>) new IsInstanceOf(type);
    }

}
