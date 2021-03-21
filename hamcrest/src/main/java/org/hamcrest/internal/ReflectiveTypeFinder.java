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
package org.hamcrest.internal;

import java.lang.reflect.Method;

/**
 * The TypeSafe classes, and their descendants, need a mechanism to find out what type has been used as a parameter 
 * for the concrete matcher. Unfortunately, this type is lost during type erasure so we need to use reflection 
 * to get it back, by picking out the type of a known parameter to a known method. 
 * The catch is that, with bridging methods, this type is only visible in the class that actually implements 
 * the expected method, so the ReflectiveTypeFinder needs to be applied to that class or a subtype.
 * 
 * For example, the abstract <code>TypeSafeDiagnosingMatcher&lt;T&gt;</code> defines an abstract method
 * <pre>protected abstract boolean matchesSafely(T item, Description mismatchDescription);</pre>
 * By default it uses <code>new ReflectiveTypeFinder("matchesSafely", 2, 0); </code> to find the
 * parameterised type. If we create a <code>TypeSafeDiagnosingMatcher&lt;String&gt;</code>, the type
 * finder will return <code>String.class</code>.
 * 
 * A <code>FeatureMatcher</code> is an abstract subclass of <code>TypeSafeDiagnosingMatcher</code>. 
 * Although it has a templated implementation of <code>matchesSafely(&lt;T&gt;, Description);</code>, the
 * actual run-time signature of this is <code>matchesSafely(Object, Description);</code>. Instead,
 * we must find the type by reflecting on the concrete implementation of 
 * <pre>protected abstract U featureValueOf(T actual);</pre>
 * a method which is declared in <code>FeatureMatcher</code>.
 * 
 * In short, use this to extract a type from a method in the leaf class of a templated class hierarchy. 
 *  
 * @author Steve Freeman
 * @author Nat Pryce
 */
public class ReflectiveTypeFinder {
  private final String methodName;
  private final int expectedNumberOfParameters;
  private final int typedParameter;

  public ReflectiveTypeFinder(String methodName, int expectedNumberOfParameters, int typedParameter) {
    this.methodName = methodName;
    this.expectedNumberOfParameters = expectedNumberOfParameters;
    this.typedParameter = typedParameter;
  }
  
  public Class<?> findExpectedType(Class<?> fromClass) {
    for (Class<?> c = fromClass; c != Object.class; c = c.getSuperclass()) {
        for (Method method : c.getDeclaredMethods()) {
            if (canObtainExpectedTypeFrom(method)) {
                return expectedTypeFrom(method);
            }
        }
    }
    throw new Error("Cannot determine correct type for " + methodName + "() method.");
  }

  /**
   * @param method The method to examine.
   * @return true if this method references the relevant type
   */
  private boolean canObtainExpectedTypeFrom(Method method) {
      return method.getName().equals(methodName)
              && method.getParameterTypes().length == expectedNumberOfParameters
              && !method.isSynthetic();
  }


  /**
   * @param method The method from which to extract
   * @return The type we're looking for
   */
  private Class<?> expectedTypeFrom(Method method) {
      return method.getParameterTypes()[typedParameter];
  }
}
