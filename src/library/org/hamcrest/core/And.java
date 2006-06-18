/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;


/**
 * Calculates the logical conjunction of two matchers. Evaluation is
 * shortcut, so that the second matcher is not called if the first
 * matcher returns <code>false</code>.
 */
public class And<T> implements Matcher<T> {
    private final Matcher<T>[] matchers;


    public And(Matcher<T>[] matchers) {
        this.matchers = matchers;
    }

    public boolean match(T o) {
        for (Matcher<T> matcher : matchers) {
            if (!matcher.match(o)) {
                return false;
            }
        }
        return true;
    }

    public void describeTo(Description description) {
        description.appendText("(");
        boolean seenFirst = false;
        for (Matcher<T> matcher : matchers) {
            if (seenFirst) {
                description.appendText(" and ");
            } else {
                seenFirst = true;
            }
            matcher.describeTo(description);
        }
        description.appendText(")");
    }

    @Factory
    public static <T> Matcher<T> and(Matcher<T>... matchers) {
        return new And<T>(matchers);
    }

}
