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
package org.hamcrest;

import junit.framework.TestCase;
import org.junit.Assert;

public abstract class AbstractMatcherTest extends TestCase {

  /**
   * Create an instance of the Matcher so some generic safety-net tests can be run on it.
   */
  protected abstract Matcher<?> createMatcher();
  
  public static <T> void assertMatches(Matcher<T> matcher, T arg) {
      assertMatches("Expected match, but mismatched", matcher, arg);
  }

  public static <T> void assertMatches(String message, Matcher<T> matcher, Object arg) {
    if (!matcher.matches(arg)) {
      Assert.fail(message + " because: '" + mismatchDescription(matcher, arg) + "'");
    }
  }

  public static <T> void assertDoesNotMatch(Matcher<? super T> c, T arg) {
      assertDoesNotMatch("Unexpected match", c, arg);
  }

  public static <T> void assertDoesNotMatch(String message, Matcher<? super T> c, T arg) {
    Assert.assertFalse(message, c.matches(arg));
  }

  public static void assertDescription(String expected, Matcher<?> matcher) {
    Description description = new StringDescription();
    description.appendDescriptionOf(matcher);
    Assert.assertEquals("Expected description", expected, description.toString().trim());
  }

  public static <T> void assertMismatchDescription(String expected, Matcher<? super T> matcher, Object arg) {
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
      } catch (Exception e) {
          Assert.fail("Matcher was not unknown type safe, because: " + e);
      }
  }

  public void testIsNullSafe() {
    assertNullSafe(createMatcher());
  }

  public void testCopesWithUnknownTypes() {
    createMatcher().matches(new UnknownType());
  }

    private static <T> String mismatchDescription(Matcher<? super T> matcher, Object arg) {
      Description description = new StringDescription();
      matcher.describeMismatch(arg, description);
      return description.toString().trim();
    }

  @SuppressWarnings("WeakerAccess")
  public static class UnknownType {
  }

}
