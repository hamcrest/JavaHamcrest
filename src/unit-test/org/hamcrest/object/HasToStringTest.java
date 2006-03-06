package org.hamcrest.object;

import junit.framework.TestCase;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class HasToStringTest extends TestCase {
    private static final String TO_STRING_RESULT = "toString result";
    private static final Object ARG = new Object() {
        public String toString() {
            return TO_STRING_RESULT;
        }
    };

    public void testPassesResultOfToStringToNestedMatcher() {
        Matcher passingMatcher = new HasToString(new IsEqual(TO_STRING_RESULT));
        Matcher failingMatcher = new HasToString(new IsEqual("OTHER STRING"));

        assertTrue("should pass", passingMatcher.match(ARG));
        assertFalse("should fail", failingMatcher.match(ARG));
    }

    public void testHasReadableDescription() {
        Matcher toStringMatcher = new IsEqual(TO_STRING_RESULT);
        Matcher matcher = new HasToString(toStringMatcher);

        assertEquals("toString(" + descriptionOf(toStringMatcher) + ")", descriptionOf(matcher));
    }

    private String descriptionOf(Matcher matcher) {
        StringBuffer buffer = new StringBuffer();
        matcher.describeTo(buffer);
        return buffer.toString();
    }
}
