/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.BaseMatcher;

import java.util.Arrays;


/**
 * Calculates the logical conjunction of two matchers. Evaluation is
 * shortcut, so that the second matcher is not called if the first
 * matcher returns <code>false</code>.
 */
public class And<T> extends BaseMatcher<T> {
    private final Iterable<Matcher<T>> matchers;

    public And(Iterable<Matcher<T>> matchers) {
        this.matchers = matchers;
    }

    public boolean matches(Object o) {
        for (Matcher<T> matcher : matchers) {
            if (!matcher.matches(o)) {
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
        return and(Arrays.asList(matchers));
    }

    @Factory
    public static <T> Matcher<T> and(Iterable<Matcher<T>> matchers) {
        return new And<T>(matchers);
    }

}
