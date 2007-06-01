/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;


public class MatcherAssert {
    public static <T> void assertThat(T actual, Matcher<T> matcher) {
        assertThat("", actual, matcher);
    }
    
    public static <T> void assertThat(String reason, T actual, Matcher<T> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription();
            description.appendText(reason)
                       .appendText("\nExpected: ")
                       .appendDescriptionOf(matcher)
                       .appendText("\n     got: ")
                       .appendValue(actual)
                       .appendText("\n");
            
            throw new java.lang.AssertionError(description.toString());
        }
    }
}
