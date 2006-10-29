package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.AbstractMatcherTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.eq;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.object.HasToString.asString;

public class HasToStringTest extends AbstractMatcherTest {

    private static final String TO_STRING_RESULT = "toString result";
    private static final Object ARG = new Object() {
        public String toString() {
            return TO_STRING_RESULT;
        }
    };

    protected Matcher<?> createMatcher() {
        return asString(eq("irrelevant"));
    }

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
