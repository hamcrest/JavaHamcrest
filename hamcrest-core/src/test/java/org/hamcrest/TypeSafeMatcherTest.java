package org.hamcrest;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;

import org.junit.Test;

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
        assertFalse(matcher.matches(10));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test public void
    describesMismatches() {
      assertMismatchDescription("was null", matcher, null);
      assertMismatchDescription("was a java.lang.Integer (<3>)", (Matcher)matcher, new Integer(3));
      assertMismatchDescription("The mismatch", matcher, "a string");
    }
}
