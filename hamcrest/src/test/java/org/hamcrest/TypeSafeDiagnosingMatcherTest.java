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

import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;

/**
 * @author Steve Freeman 2016 http://www.hamcrest.com
 */
@SuppressWarnings("WeakerAccess")
public class TypeSafeDiagnosingMatcherTest {

    @Test public void
    describesMismatches() {
        assertMismatchDescription("was null", STRING_MATCHER, null);
        assertMismatchDescription("was Character \"c\"", STRING_MATCHER, 'c');
        assertMismatchDescription("mismatching", STRING_MATCHER, "other");
    }

    @Test public void
    detects_non_builtin_types() {
      final Matcher<NotBuiltIn> matcher = new TypeSafeDiagnosingMatcher<NotBuiltIn>() {
        @Override
        protected boolean matchesSafely(NotBuiltIn item, Description mismatchDescription) {
          return true;
        }

        @Override public void describeTo(Description description) { description.appendText("a not builtin"); }
      };

      assertMatches("not built in", matcher, new NotBuiltIn());
      assertDoesNotMatch("other not built in", (Matcher)matcher, new OtherNotBuiltIn());
    }

    @Test public void
    filters_type_for_subclassed_matcher_when_expected_type_passed_in() {
      final Matcher<NotBuiltIn> matcher = new SubMatcher<>(new NotBuiltIn());

      assertMatches("not built in", matcher, new NotBuiltIn());
      assertDoesNotMatch("other not built in", (Matcher)matcher, new OtherNotBuiltIn());
  }

    @Test public void
    but_cannot_detect_generic_type_in_subclassed_matcher_using_reflection() {
        final Matcher<NotBuiltIn> matcher = new SubMatcher<>();

        assertMatches("not built in", matcher, new NotBuiltIn());
        assertMatches("other not built in", (Matcher)matcher, new OtherNotBuiltIn());
    }

    private static final TypeSafeDiagnosingMatcher STRING_MATCHER = new TypeSafeDiagnosingMatcher<String>() {
        @Override
        protected boolean matchesSafely(String item, Description mismatchDescription) {
          mismatchDescription.appendText("mismatching");
          return false;
        }

        @Override
        public void describeTo(Description description) { }
    };

    public static class SubMatcher<T> extends TypeSafeDiagnosingMatcher<T> {
      public SubMatcher() {
          super();
      }
      public SubMatcher(T expectedObject) {
          super(expectedObject.getClass());
      }
      @Override protected boolean matchesSafely(T item, Description mismatchDescription) { return true; }
      @Override public void describeTo(Description description) { description.appendText("sub type"); }
    }

    public static class NotBuiltIn {
      public final String value = "not built in";
      @Override public String toString() { return "NotBuiltIn"; }
    }

    public static class OtherNotBuiltIn { // empty
    }

}
