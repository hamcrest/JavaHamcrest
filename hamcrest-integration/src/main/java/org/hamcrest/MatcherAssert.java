/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;


public class MatcherAssert {

    public static <T,K extends T,V extends T> void assertThat(K actual, Matcher<V> matcher) {
        assertThat("", actual, matcher);
    }

    public static<T,K extends T,V extends T> void assertThat(String reason, K actual, Matcher<V> matcher) {
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
    
    public static void assertThat(String reason, boolean assertion) {
        if (!assertion) {
            throw new AssertionError(reason);
        }
    }
}
