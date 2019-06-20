package org.hamcrest;


/**
 * Utility class that contains static methods for verifying that assertions 
 * hold and reporting the violation when they do not.
 */
public class MatcherAssert {
	
    /**
     * Verifies that <code>matcher</code> matches <code>actual</code>. 
     * Convenience method for performing an assertion without supplying a reason.
     * Equivalent to <code>assertThat("", actual, matcher)</code>.
     * 
     * @see {@link MatcherAssert#assertThat(String, Object, Matcher)}
     * 
     * @param <T> The type of the actual value to match.
     * @param actual A value to test.
     * @param matcher The matcher to use for matching the actual value
     */
    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }
    
    /**
     * Verifies that <code>matcher</code> matches <code>actual</code>. If the matcher
     * does not match the actual value, this method throws an <code>AssertionError</code>
     * with a description of the mismatch.
     * 
     * @param <T> The type of the actual value to match.
     * @param reason A reason for explaining the mismatch.
     * @param actual A value to test.
     * @param matcher The matcher to use for matching the actual value
     */
    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription();
            description.appendText(reason)
                       .appendText("\nExpected: ")
                       .appendDescriptionOf(matcher)
                       .appendText("\n     but: ");
            matcher.describeMismatch(actual, description);
            
            throw new AssertionError(description.toString());
        }
    }
    
    /**
     * Verifies that <code>assertion</code> evaluates to <code>true</code>, and
     * throws an <code>AssertionError</code> with the message <code>reason</code>
     * if it does not.
     * 
     * @param reason The message use to report an assertion failure.
     * @param assertion An expression expected to evaluate to <code>true</code>.
     */
    public static void assertThat(String reason, boolean assertion) {
        if (!assertion) {
            throw new AssertionError(reason);
        }
    }
}
