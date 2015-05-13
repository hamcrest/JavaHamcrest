package org.hamcrest;

/**
 * Provides assertions for use in unit tests etc
 */
public class MatcherAssert {
	/**
	 * Assert that a value is matched by a matcher
	 * @param actual actual value
	 * @param matcher to match against the actual
	 * @throws AssertionError if the matcher does not match with the actual
	 */
    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }
    
	/**
	 * Assert that a value is matched by a matcher, with a reason
	 * explaining the meaning of the assertion
	 * @param reason a description of the meaning of the assertion
	 * @param actual actual value
	 * @param matcher to match against the actual
	 * @throws AssertionError if the matcher does not match with the actual
	 */
    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
    	assertThat(selfDescribingString(reason), actual, matcher);
    }
    
	/**
	 * Assert that a value is matched by a matcher, with a describer
	 * to explain the meaning of the assertion. The interface for the
	 * reason it intended for providing a description just in time
	 * rather than requiring the caller to calculate the description ahead of
	 * it being needed in the assertion message. The aim is to allow
	 * a lambda to be provided to describe the error if required, e.g.
	 *     assertThat(d->d.appendText("Checking validity of ").appendText(something.toString(), 
	 *         someValue, is(expectedValue));
	 * @param reason an object that can describe the meaning of the assertion
	 * @param actual actual value
	 * @param matcher to match against the actual
	 * @throws AssertionError if the matcher does not match with the actual
	 */
    public static <T> void assertThat(SelfDescribing reason, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription();
            reason.describeTo(description);
            description.appendText("\nExpected: ")
                       .appendDescriptionOf(matcher)
                       .appendText("\n     but: ");
            matcher.describeMismatch(actual, description);
            
            throw new AssertionError(description.toString());
        }
    }
    
    /**
     * Assert that a value is true
     * @param reason description of what is being tested
     * @param assertion a value that is expected to be true for
     * @throws AssertionError if assertion is false
     */
    public static void assertThat(String reason, boolean assertion) {
    	assertThat(selfDescribingString(reason), assertion);
    }

    /**
     * Assert that a value is true
     * @param reason an object that can describe the reason for the test
     * @param assertion a value that is expected to be true
     * @throws AssertionError if assertion is false
     */
	public static void assertThat(SelfDescribing reason, boolean assertion) {
        if (!assertion) {
        	Description description = new StringDescription();
        	reason.describeTo(description);
            throw new AssertionError(description.toString());
        }
    }
    
    private static SelfDescribing selfDescribingString(final String reason) {
		return new SelfDescribing() {
			@Override
			public void describeTo(Description description) {
				description.appendText(reason);
			}
			
		};
	}    
}
