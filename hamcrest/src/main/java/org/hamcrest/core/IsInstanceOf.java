/**
 * BSD License
 *
 * Copyright (c) 2000-2021 www.hamcrest.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * Neither the name of Hamcrest nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
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
     * For example:
     * <pre>assertThat(new Canoe(), instanceOf(Paddlable.class));</pre>
     * 
     */
    @SuppressWarnings("unchecked")
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
     * For example:
     * <pre>assertThat(new Canoe(), instanceOf(Canoe.class));</pre>
     *
     */
    @SuppressWarnings("unchecked")
    public static <T> Matcher<T> any(Class<T> type) {
        return (Matcher<T>) new IsInstanceOf(type);
    }

}
