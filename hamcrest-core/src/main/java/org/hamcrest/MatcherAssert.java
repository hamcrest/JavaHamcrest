/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;


import static org.hamcrest.MatcherExecute.failWithAssertionError;
import static org.hamcrest.MatcherExecute.match;

public class MatcherAssert {
    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }

    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        match(reason, actual, matcher, failWithAssertionError());
    }

    public static void assertThat(String reason, boolean assertion) {
        if (!assertion) {
            throw new AssertionError(reason);
        }
    }
}
