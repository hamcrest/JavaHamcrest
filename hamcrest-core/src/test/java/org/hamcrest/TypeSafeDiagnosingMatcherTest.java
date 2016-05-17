package org.hamcrest;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;

/**
 * @author Steve Freeman 2016 http://www.hamcrest.com
 */
public class TypeSafeDiagnosingMatcherTest {

  private final TypeSafeDiagnosingMatcher stringMatcher = new TypeSafeDiagnosingMatcher<String>() {
    @Override
    protected boolean matchesSafely(String item, Description mismatchDescription) {
      mismatchDescription.appendText("mismatching");
      return false;
    }

    @Override
    public void describeTo(Description description) { }
  };


  @Test public void
  describesMismatches() {
    assertMismatchDescription("was null", stringMatcher, null);
    assertMismatchDescription("was Character \"c\"", stringMatcher, 'c');
    assertMismatchDescription("mismatching", stringMatcher, "other");
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

  @Ignore("not yet") @Test public void
  detects_for_subtypes_of_matcher() {
      final Matcher<NotBuiltIn> matcher = new SubMatcher<>();

      assertMatches("not built in", matcher, new NotBuiltIn());
      assertDoesNotMatch("other not built in", (Matcher)matcher, new OtherNotBuiltIn());
  }


  public static class SubMatcher<T> extends TypeSafeDiagnosingMatcher<T> {
    @Override protected boolean matchesSafely(T item, Description mismatchDescription) { return true; }

    @Override public void describeTo(Description description) { description.appendText("sub type"); }
  }

  public static class NotBuiltIn {
      public final String value = "not built in";
      @Override public String toString() { return "NotBuiltIn"; }
  }

  public static class OtherNotBuiltIn {

  }

}
