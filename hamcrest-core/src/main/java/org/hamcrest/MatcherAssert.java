package org.hamcrest;

public class MatcherAssert {
    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }
    
    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
    	assertThat(selfDescribingString(reason), actual, matcher);
    }
    
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
    
    public static void assertThat(String reason, boolean assertion) {
    	assertThat(selfDescribingString(reason), assertion);
    }
    
    private static SelfDescribing selfDescribingString(final String reason) {
		return new SelfDescribing() {
			@Override
			public void describeTo(Description description) {
				description.appendText(reason);
			}
			
		};
	}

	public static void assertThat(SelfDescribing reason, boolean assertion) {
        if (!assertion) {
        	Description description = new StringDescription();
        	reason.describeTo(description);
            throw new AssertionError(description.toString());
        }
    }
    
    public static SelfDescribing describeAsString(final String string) {
    	return new SelfDescribing() {
			@Override
			public void describeTo(Description description) {
				description.appendText(string);
			}
    	};
    }
}
