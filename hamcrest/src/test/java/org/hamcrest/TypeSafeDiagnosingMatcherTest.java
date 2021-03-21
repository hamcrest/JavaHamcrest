package org.hamcrest;

import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;

/**
 * @author Steve Freeman 2016 http://www.hamcrest.com
 */
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
