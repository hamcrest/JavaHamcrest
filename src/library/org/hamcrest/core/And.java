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
    private final Matcher<T> left;
    private final Matcher<T> right;

    public And(Matcher<T> left, Matcher<T> right) {
        this.left = left;
        this.right = right;
    }

    public boolean match(T o) {
        return left.match(o) && right.match(o);
    }

    public void describeTo(Description description) {
        description.appendText("(");
        left.describeTo(description);
        description.appendText(" and ");
        right.describeTo(description);
        description.appendText(")");
    }

    @Factory
    public static <T> Matcher<T> and(Matcher<T> left, Matcher<T> right) {
        return new And<T>(left, right);
    }

}
