package org.hamcrest.object;

import junit.framework.TestCase;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.eq;
import static org.hamcrest.core.IsNot.not;
import org.hamcrest.internal.StringDescription;
import static org.hamcrest.object.HasToString.asString;

public class HasToStringTest extends TestCase {

    private static final String TO_STRING_RESULT = "toString result";
    private static final Object ARG = new Object() {
        public String toString() {
            return TO_STRING_RESULT;
        }
    };

    public void testPassesResultOfToStringToNestedMatcher() {
        assertThat(ARG, asString(eq(TO_STRING_RESULT)));
        assertThat(ARG, not(asString(eq("OTHER STRING"))));
    }

    public void testHasReadableDescription() {
        Matcher<String> toStringMatcher = eq(TO_STRING_RESULT);
        Matcher matcher = asString(toStringMatcher);

        assertEquals("asString(" + descriptionOf(toStringMatcher) + ")", descriptionOf(matcher));
    }

    private String descriptionOf(Matcher matcher) {
        Description description = new StringDescription();
        matcher.describeTo(description);
        return description.toString();
    }

}
