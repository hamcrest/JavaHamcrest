package org.hamcrest;

import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public final class TypeSafeDiagnosingMatcherTest {
    private final Matcher<String> matcher = new TypeSafeDiagnosingMatcherSubclass();

    public static class TypeSafeDiagnosingMatcherSubclass extends TypeSafeDiagnosingMatcher<String> {

        @Override
        protected boolean matchesSafely(String item, Description mismatchDescription) {
            mismatchDescription.appendText("The mismatch");
            return false;
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
      assertMismatchDescription("was a java.lang.Integer (<3>)", (Matcher)matcher, 3);
      assertMismatchDescription("The mismatch", matcher, "a string");
    }

    @Test public void
    hasCorrectParameterType() {
        assertEquals(String.class, matcher.getParameterType());
    }
}
