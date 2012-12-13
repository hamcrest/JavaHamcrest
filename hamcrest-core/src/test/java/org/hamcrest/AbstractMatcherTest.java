/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;

import junit.framework.TestCase;
import org.junit.Assert;

public abstract class AbstractMatcherTest extends TestCase {

  /**
   * Create an instance of the Matcher so some generic safety-net tests can be run on it.
   */
  protected abstract Matcher<?> createMatcher();

  public static <T> void assertMatches(String message, Matcher<? super T> matcher, T arg) {
    if (!matcher.matches(arg)) {
      Assert.fail(message + " because: '" + mismatchDescription(matcher, arg) + "'");
    }
  }

  public static <T> void assertDoesNotMatch(String message, Matcher<? super T> c, T arg) {
    Assert.assertFalse(message, c.matches(arg));
  }

  public static void assertDescription(String expected, Matcher<?> matcher) {
    Description description = new StringDescription();
    description.appendDescriptionOf(matcher);
    Assert.assertEquals("Expected description", expected, description.toString().trim());
  }

  public static <T> void assertMismatchDescription(String expected, Matcher<? super T> matcher, T arg) {
    Assert.assertFalse("Precondition: Matcher should not match item.", matcher.matches(arg));
    Assert.assertEquals("Expected mismatch description", expected, mismatchDescription(matcher, arg));
  }

  private static <T> String mismatchDescription(Matcher<? super T> matcher, T arg) {
    Description description = new StringDescription();
    matcher.describeMismatch(arg, description);
    return description.toString().trim();
  }

  public void testIsNullSafe() {
    // should not throw a NullPointerException
    createMatcher().matches(null);
  }

  public void testCopesWithUnknownTypes() {
    // should not throw ClassCastException
    createMatcher().matches(new UnknownType());
  }

  public static class UnknownType {
  }

}
