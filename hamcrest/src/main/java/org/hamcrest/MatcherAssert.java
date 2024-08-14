package org.hamcrest;

/**
 * The Hamcrest entrypoint, static methods to check if matchers match a
 * given value.
 */
public class MatcherAssert {

    private MatcherAssert() {
    }

    /**
     * Checks that a value matches a matcher
     * @param actual the value to check
     * @param matcher the matcher
     * @param <T> the type of the value
     */
    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }

    /**
     * Checks that a value matches a matcher
     * @param reason a description of what is being matched
     * @param actual the value to check
     * @param matcher the matcher
     * @param <T> the type of the value
     */
    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription();
            description.appendText(reason)
                       .appendText(System.lineSeparator())
                       .appendText("Expected: ")
                       .appendDescriptionOf(matcher)
                       .appendText(System.lineSeparator())
                       .appendText("     but: ");
            matcher.describeMismatch(actual, description);

            throw new AssertionError(description.toString());
        }
    }

    /**
     * Checks that an assertion is true
     * @param reason a description of what is being checked
     * @param assertion the result of the check
     */
    public static void assertThat(String reason, boolean assertion) {
        if (!assertion) {
            throw new AssertionError(reason);
        }
    }

}
