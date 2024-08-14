package org.hamcrest.internal;

import java.lang.reflect.Method;

/**
 * <p>Find the declared type of a parameterized type at runtime, bypassing normal type erasure problems.
 * </p>
 * <p>The TypeSafe classes, and their descendants, need a mechanism to find out what type has been used as a parameter
 * for the concrete matcher. Unfortunately, this type is lost during type erasure, so we need to use reflection
 * to get it back, by picking out the type of a known parameter to a known method.
 * The catch is that, with bridging methods, this type is only visible in the class that actually implements
 * the expected method, so the ReflectiveTypeFinder needs to be applied to that class or a subtype.
 * </p>
 * <p>For example, the abstract <code>TypeSafeDiagnosingMatcher&lt;T&gt;</code> defines an abstract method
 * <code>protected abstract boolean matchesSafely(T item, Description mismatchDescription);</code>
 * By default, it uses <code>new ReflectiveTypeFinder("matchesSafely", 2, 0);</code> to find the
 * parameterised type. If we create a <code>TypeSafeDiagnosingMatcher&lt;String&gt;</code>, the type
 * finder will return <code>String.class</code>.
 * </p>
 * <p>Another example: a <code>FeatureMatcher</code> is an abstract subclass of <code>TypeSafeDiagnosingMatcher</code>.
 * Although it has a templated implementation of <code>matchesSafely(&lt;T&gt;, Description);</code>, the
 * actual run-time signature of this is <code>matchesSafely(Object, Description);</code>. Instead,
 * we must find the type by reflecting on the concrete implementation of
 * <code>protected abstract U featureValueOf(T actual);</code>,
 * a method which is declared in <code>FeatureMatcher</code>.
 * </p>
 * <p>In short, use this to extract a type from a method in the leaf class of a templated class hierarchy.
 * </p>
 *
 * @author Steve Freeman
 * @author Nat Pryce
 */
public class ReflectiveTypeFinder {

  private final String methodName;
  private final int expectedNumberOfParameters;
  private final int typedParameter;

    /**
     * Create a <code>ReflectiveTypeFinder</code> for a specific parameter on a specific method.
     * @param methodName the name of a method in the leaf class that has a templated type
     * @param expectedNumberOfParameters the expected number of parameters to the method
     * @param typedParameter the index of the parameter that has the type information we want to look up
     */
  public ReflectiveTypeFinder(String methodName, int expectedNumberOfParameters, int typedParameter) {
    this.methodName = methodName;
    this.expectedNumberOfParameters = expectedNumberOfParameters;
    this.typedParameter = typedParameter;
  }

    /**
     * Find the parameterized type from the specified parameter on the specified method.
     * @param fromClass the class containing the method with the parameterized type
     * @return the method parameter type
     */
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
