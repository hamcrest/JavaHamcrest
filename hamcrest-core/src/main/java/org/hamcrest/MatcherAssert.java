package org.hamcrest;


public class MatcherAssert {
    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }
    
    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
    	assertThat(justInTimeString(reason), actual, matcher);
    }
    
    public static <T> void assertThat(JustInTimeMessage reason, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription();
            description.appendText(reason.getMessage())
                       .appendText("\nExpected: ")
                       .appendDescriptionOf(matcher)
                       .appendText("\n     but: ");
            matcher.describeMismatch(actual, description);
            
            throw new AssertionError(description.toString());
        }
    }
    
    public static void assertThat(String reason, boolean assertion) {
    	assertThat(justInTimeString(reason), assertion);
    }
    
    public static void assertThat(JustInTimeMessage reason, boolean assertion) {
        if (!assertion) {
            throw new AssertionError(reason.getMessage());
        }
    }
    
    public static JustInTimeMessage justInTimeString(final String string) {
    	return new JustInTimeMessage() {
    		public String getMessage() { return string; }
    	};
    }
}
