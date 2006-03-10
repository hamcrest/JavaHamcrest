package org.hamcrest.object;

import junit.framework.TestCase;
import org.hamcrest.Matcher;
import org.hamcrest.Description;
import org.hamcrest.internal.StringDescription;
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
        Description description = new StringDescription();
        matcher.describeTo(description);
        return description.toString();
    }
}
