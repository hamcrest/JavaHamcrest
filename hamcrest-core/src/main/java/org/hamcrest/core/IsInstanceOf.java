/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;


/**
 * Tests whether the value is an instance of a class.
 * Classes of basic types will be converted to the relevant "Object" classes
 */
public class IsInstanceOf extends DiagnosingMatcher<Object> {
    private final Class<?> expectedClass;
    private final Class<?> matchableClass;

    /**
     * Creates a new instance of IsInstanceOf
     *
     * @param expectedClass The predicate evaluates to true for instances of this class
     *                 or one of its subclasses.
     */
    public IsInstanceOf(Class<?> expectedClass) {
        this.expectedClass = expectedClass;
        this.matchableClass = matchableClass(expectedClass);
    }

    private static Class<?> matchableClass(Class<?> expectedClass) {
      if (boolean.class.equals(expectedClass)) return Boolean.class; 
      if (byte.class.equals(expectedClass)) return Byte.class; 
      if (char.class.equals(expectedClass)) return Character.class; 
      if (double.class.equals(expectedClass)) return Double.class; 
      if (float.class.equals(expectedClass)) return Float.class; 
      if (int.class.equals(expectedClass)) return Integer.class; 
      if (long.class.equals(expectedClass)) return Long.class; 
      if (short.class.equals(expectedClass)) return Short.class; 
      return expectedClass;
    }

    @Override
    protected boolean matches(Object item, Description mismatch) {
      if (null == item) {
        mismatch.appendText("null");
        return false;
      }
      
      if (!matchableClass.isInstance(item)) {
        mismatch.appendValue(item).appendText(" is a " + item.getClass().getName());
        return false;
      }
      
      return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an instance of ").appendText(expectedClass.getName());
    }

    /**
     * Creates a matcher that matches when the examined object is an instance of the specified <code>type</code>,
     * as determined by calling the {@link java.lang.Class#isInstance(Object)} method on that type, passing the
     * the examined object.
     * 
     * <p>The created matcher assumes no relationship between specified type and the examined object.</p>
     * <p/>
     * For example: 
     * <pre>assertThat(new Canoe(), instanceOf(Paddlable.class));</pre>
     * 
     */
    @SuppressWarnings("unchecked")
    @Factory
    public static <T> Matcher<T> instanceOf(Class<?> type) {
        return (Matcher<T>) new IsInstanceOf(type);
    }
    
    /**
     * Creates a matcher that matches when the examined object is an instance of the specified <code>type</code>,
     * as determined by calling the {@link java.lang.Class#isInstance(Object)} method on that type, passing the
     * the examined object.
     * 
     * <p>The created matcher forces a relationship between specified type and the examined object, and should be
     * used when it is necessary to make generics conform, for example in the JMock clause
     * <code>with(any(Thing.class))</code></p>
     * <p/>
     * For example: 
     * <pre>assertThat(new Canoe(), instanceOf(Canoe.class));</pre>
     *
     */
    @SuppressWarnings("unchecked")
    @Factory
    public static <T> Matcher<T> any(Class<T> type) {
        return (Matcher<T>) new IsInstanceOf(type);
    }

}
