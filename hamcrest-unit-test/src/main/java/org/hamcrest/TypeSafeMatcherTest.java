package org.hamcrest;

import junit.framework.TestCase;

public class TypeSafeMatcherTest extends TestCase {
    public static class TypeSafeMatcherSubclass extends TypeSafeMatcher<String> {
        @Override
        protected boolean matchesSafely(String item) {
            return false;
        }

        public void describeTo(Description description) {
        }
    }
    
    
    public void testCanDetermineMatcherTypeFromProtectedMatchesSafelyMethod() {
        Matcher<String> matcher = new TypeSafeMatcherSubclass();
        
        assertFalse(matcher.matches(null));
        assertFalse(matcher.matches(10));
    }
}
