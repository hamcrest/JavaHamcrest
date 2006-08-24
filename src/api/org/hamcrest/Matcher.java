/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;

/**
 * A matcher over acceptable values. A matcher should be able to describe itself to give feedback when it fails.
 */
public interface Matcher<T> {
    /**
     * Evaluates the matcher for argument <var>item</var>.
     *
     * @param item the object against which the matcher is evaluated.
     * @return <code>true</code> if <var>item</var> matches, otherwise <code>false</code>.
     */
    boolean match(T item);

    /**
     * Describes this matcher.
     */
    void describeTo(Description description);
}
