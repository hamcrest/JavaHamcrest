package org.hamcrest;

import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.junit.Assert.assertFalse;

@SuppressWarnings("WeakerAccess")
public final class TypeSafeMatcherTest {
    private final Matcher<String> matcher = new TypeSafeMatcherSubclass();

    public static class TypeSafeMatcherSubclass extends TypeSafeMatcher<String> {
        @Override
        public boolean matchesSafely(String item) {
            return false;
        }

        @Override
        public void describeMismatchSafely(String item, Description mismatchDescription) {
          mismatchDescription.appendText("The mismatch");
        }

        @Override
        public void describeTo(Description description) {
        }
    }

    @Test public void
    canDetermineMatcherTypeFromProtectedMatchesSafelyMethod() {
        assertFalse(matcher.matches(null));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test public void
    describesMismatches() {
      assertMismatchDescription("was null", matcher, null);
      assertMismatchDescription("was a java.lang.Integer (<3>)", (Matcher)matcher, 3);
      assertMismatchDescription("The mismatch", matcher, "a string");
    }
}
