/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;

/**
 * A matcher over acceptable values. A matcher should be able to describe itself to give feedback when it fails.
 */
public interface Matcher {
    /**
     * Evaluates the matcher for argument <var>o</var>.
     *
     * @param o the object against which the matcher is evaluated.
     * @return <code>true</code> if <var>o</var> matcher, otherwise <code>false</code>.
     */
    boolean match(Object o);

    /**
     * Describes this matcher.
     */
    void describeTo(Description description);
}
