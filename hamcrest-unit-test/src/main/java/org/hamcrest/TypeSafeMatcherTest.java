package org.hamcrest;

import junit.framework.TestCase;

public class TypeSafeMatcherTest extends TestCase {
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
    
    public void testCanDetermineMatcherTypeFromProtectedMatchesSafelyMethod() {
        assertFalse(matcher.matches(null));
        assertFalse(matcher.matches(10));
    }
    
    public void testDescribesMismatches() {
      assertMismatchDescription("was null", null);
      assertMismatchDescription("was a java.lang.Integer (<3>)", new Integer(3));
      assertMismatchDescription("The mismatch", "a string");
    }

    private void assertMismatchDescription(String expectedDescription, Object actual) {
      StringDescription description = new StringDescription();
      matcher.describeMismatch(actual, description);
      assertEquals(expectedDescription, description.toString());
    }
}
