package org.hamcrest.object;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.object.HasToString.hasToString;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class HasToStringTest extends AbstractMatcherTest {
    private static final String TO_STRING_RESULT = "toString result";
    private static final Object ARG = new Object() {
        public String toString() {
            return TO_STRING_RESULT;
        }
    };

    protected Matcher<?> createMatcher() {
        return hasToString(equalTo("irrelevant"));
    }

    public void testPassesResultOfToStringToNestedMatcher() {
        assertThat(ARG, hasToString(equalTo(TO_STRING_RESULT)));
        assertThat(ARG, not(hasToString(equalTo("OTHER STRING"))));
    }

    public void testProvidesConvenientShortcutForHasToStringEqualTo() {
        assertThat(ARG, hasToString(TO_STRING_RESULT));
        assertThat(ARG, not(hasToString("OTHER STRING")));
    }

    public void testHasReadableDescription() {
        Matcher<String> toStringMatcher = equalTo(TO_STRING_RESULT);
        Matcher<Matcher<String>> matcher = hasToString(toStringMatcher);

        assertEquals("asString(" + descriptionOf(toStringMatcher) + ")", descriptionOf(matcher));
    }

    private String descriptionOf(Matcher<?> matcher) {
        return StringDescription.asString(matcher);
    }
}
