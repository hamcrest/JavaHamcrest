/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;


/**
 * Calculates the logical negation of a matcher.
 */
public class IsNot<T> implements Matcher<T> {
    private final Matcher<T> matcher;

    public IsNot(Matcher<T> matcher) {
        this.matcher = matcher;
    }

    public boolean match(Object arg) {
        return !matcher.match(arg);
    }

    public void describeTo(Description description) {
        description.appendText("not ");
        matcher.describeTo(description);
    }

    @Factory
    public static <T> Matcher<T> not(Matcher<T> matcher) {
        return new IsNot<T>(matcher);
    }

}
