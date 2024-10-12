package org.hamcrest.test;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import static org.junit.jupiter.api.Assertions.*;

public interface MatcherAssertions {

    static <T> void assertMatches(Matcher<T> matcher, T arg) {
        assertMatches("Expected match, but mismatched", matcher, arg);
    }

    static <T> void assertMatches(String message, Matcher<T> matcher, Object arg) {
        if (!matcher.matches(arg)) {
            fail(message + " because: '" + mismatchDescription(matcher, arg) + "'");
        }
    }

    static <T> void assertDoesNotMatch(Matcher<? super T> c, T arg) {
        assertDoesNotMatch("Unexpected match", c, arg);
    }

    static <T> void assertDoesNotMatch(String message, Matcher<? super T> c, T arg) {
        assertFalse(c.matches(arg), message);
    }

    static void assertDescription(String expected, Matcher<?> matcher) {
        Description description = new StringDescription();
        description.appendDescriptionOf(matcher);
        assertEquals(expected, description.toString().trim(), "Expected description");
    }

    static <T> void assertMismatchDescription(String expected, Matcher<? super T> matcher, Object arg) {
        assertFalse(matcher.matches(arg), "Precondition: Matcher should not match item.");
        assertEquals(expected, mismatchDescription(matcher, arg), "Expected mismatch description");
    }

    static void assertNullSafe(Matcher<?> matcher) {
        try {
            matcher.matches(null);
        } catch (Exception e) {
            fail("Matcher was not null safe");
        }
    }

    static void assertUnknownTypeSafe(Matcher<?> matcher) {
        try {
            matcher.matches(new UnknownType());
        } catch (Exception e) {
            fail("Matcher was not unknown type safe, because: " + e);
        }
    }

    static <T> String mismatchDescription(Matcher<? super T> matcher, Object arg) {
        Description description = new StringDescription();
        matcher.describeMismatch(arg, description);
        return description.toString().trim();
    }
}

@SuppressWarnings("WeakerAccess")
class UnknownType {
}
