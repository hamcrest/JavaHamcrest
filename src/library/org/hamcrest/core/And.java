/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;


/**
 * Calculates the logical conjunction of two matchers. Evaluation is
 * shortcut, so that the second matcher is not called if the first
 * matcher returns <code>false</code>.
 */
public class And implements Matcher {
    private final Matcher left;
    private final Matcher right;

    public And(Matcher left, Matcher right) {
        this.left = left;
        this.right = right;
    }

    public boolean match(Object o) {
        return left.match(o) && right.match(o);
    }

    public void describeTo(Description description) {
        description.appendText("(");
        left.describeTo(description);
        description.appendText(" and ");
        right.describeTo(description);
        description.appendText(")");
    }
}
