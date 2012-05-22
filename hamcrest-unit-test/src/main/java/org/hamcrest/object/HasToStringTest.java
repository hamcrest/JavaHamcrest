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
        @Override
        public String toString() {
            return TO_STRING_RESULT;
        }
    };

    @Override
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
        Matcher<? super String> toStringMatcher = equalTo(TO_STRING_RESULT);
        Matcher<Matcher<String>> matcher = hasToString(toStringMatcher);

        assertEquals("with toString() " + descriptionOf(toStringMatcher), descriptionOf(matcher));
    }

    public void testMismatchContainsToStringValue() {
        String expectedMismatchString = "toString() was \"Cheese\"";
        assertMismatchDescription(expectedMismatchString, hasToString(TO_STRING_RESULT), "Cheese");
    }

    private static String descriptionOf(Matcher<?> matcher) {
        return StringDescription.asString(matcher);
    }
}
