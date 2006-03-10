/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.hamcrest.Description;


/**
 * Calculates the logical negation of a matcher.
 */
public class IsNot implements Matcher {
    private final Matcher matcher;

    public IsNot(Matcher matcher) {
        this.matcher = matcher;
    }

    public boolean match(Object arg) {
        return !matcher.match(arg);
    }

    public void describeTo(Description description) {
        description.append("not ");
        matcher.describeTo(description);
    }
}
