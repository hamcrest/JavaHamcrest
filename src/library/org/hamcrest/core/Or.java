/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import static java.util.Arrays.asList;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;

/**
 * Calculates the logical disjunction of two matchers. Evaluation is
 * shortcut, so that the second matcher is not called if the first
 * matcher returns <code>true</code>.
 */
public class Or<T> implements Matcher<T> {

    private final Iterable<Matcher<T>> matchers;

    public Or(Iterable<Matcher<T>> matchers) {
        this.matchers = matchers;
    }

    public boolean matches(Object o) {
        for (Matcher<T> matcher : matchers) {
            if (matcher.matches(o)) {
                return true;
            }
        }
        return false;
    }

    public void describeTo(Description description) {
        description.appendText("(");
        boolean seenFirst = false;
        for (Matcher<T> matcher : matchers) {
            if (seenFirst) {
                description.appendText(" or ");
            } else {
                seenFirst = true;
            }
            matcher.describeTo(description);
        }
        description.appendText(")");
    }

    @Factory
    public static <T> Matcher<T> or(Matcher<T>... matchers) {
        return new Or<T>(asList(matchers));
    }

    @Factory
    public static <T> Matcher<T> or(Iterable<Matcher<T>> matchers) {
        return new Or<T>(matchers);
    }
}
