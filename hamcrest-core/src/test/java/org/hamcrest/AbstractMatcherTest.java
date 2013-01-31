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

  public static void assertMatches(String message, Matcher<?> matcher, Object arg) {
    if (!matcher.matches(arg)) {
      Assert.fail(message + " because: '" + mismatchDescription(matcher, arg) + "'");
    }
  }

  public static void assertDoesNotMatch(String message, Matcher<?> c, Object arg) {
    Assert.assertFalse(message, c.matches(arg));
  }

  public static void assertDescription(String expected, Matcher<?> matcher) {
    Description description = new StringDescription();
    description.appendDescriptionOf(matcher);
    Assert.assertEquals("Expected description", expected, description.toString().trim());
  }

  public static void assertMismatchDescription(String expected, Matcher<?> matcher, Object arg) {
    Assert.assertFalse("Precondition: Matcher should not match item.", matcher.matches(arg));
    Assert.assertEquals("Expected mismatch description", expected, mismatchDescription(matcher, arg));
  }
  
  public static void assertNullSafe(Matcher<?> matcher) {
      try {
          matcher.matches(null);
      }
      catch (Exception e) {
          Assert.fail("Matcher was not null safe");
      }
  }

  public static void assertUnknownTypeSafe(Matcher<?> matcher) {
      try {
          matcher.matches(new UnknownType());
      }
      catch (Exception e) {
          Assert.fail("Matcher was not unknown type safe");
      }
  }

  private static String mismatchDescription(Matcher<?> matcher, Object arg) {
    Description description = new StringDescription();
    matcher.describeMismatch(arg, description);
    return description.toString().trim();
  }

  public void testIsNullSafe() {
    assertNullSafe(createMatcher());
  }

  public void testCopesWithUnknownTypes() {
    assertUnknownTypeSafe(createMatcher());
  }

  public static class UnknownType {
  }

}
