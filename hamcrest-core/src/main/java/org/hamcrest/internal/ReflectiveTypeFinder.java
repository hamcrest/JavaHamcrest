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
 * Although it has a templated implementation of <code>matchesSafely(&lt;T&gt;, Decription);</code>, the  
 * actualy run-time signature of this is <code>matchesSafely(Object, Description);</code>. Instead,
 * we must find the type by reflecting on the concrete implementation of 
 * <pre>protected abstract U featureValueOf(T actual);</pre>
 * a method which is declared in <code>FeatureMatcher</code>.
 * 
 * In short, use this to extract a type from a method in the leaf class of a templated class hierarchy. 
 *  
 * @author Steve Freeman
 * @author Nat Pryce
 */
package org.hamcrest.internal;

import java.lang.reflect.Method;

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
  protected boolean canObtainExpectedTypeFrom(Method method) {
      return method.getName().equals(methodName)
              && method.getParameterTypes().length == expectedNumberOfParameters
              && !method.isSynthetic();
  }


  /**
   * @param method The method from which to extract
   * @return The type we're looking for
   */
  protected Class<?> expectedTypeFrom(Method method) {
      return method.getParameterTypes()[typedParameter];
  }
}