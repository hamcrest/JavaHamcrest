/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;

/**
 * A matcher over acceptable values.
 * A matcher is able to describe itself to give feedback when it fails.
 * <p/>
 * <p/>
 * Matcher implementations should <b>NOT directly implement this interface</b>.
 * Instead, <b>extend</b> the <b>BaseMatcher</b> abstract class, which will ensure that
 * the Matcher API can grow to support new features and remain compatible with all
 * Matcher implementations.
 *
 * @see BaseMatcher
 */
@SuppressWarnings({"unused", "UnusedDeclaration"})
public interface Matcher<T> extends SelfDescribing {

    /**
     * Evaluates the matcher for argument <var>item</var>.
     *
     * @param item the object against which the matcher is evaluated.
     * @return <code>true</code> if <var>item</var> matches, otherwise <code>false</code>.
     */
    boolean matches(Object item);

    /**
     * This method simply acts a friendly reminder not to implement Matcher directly and
     * instead extend BaseMatcher. It's easy to ignore JavaDoc, but a bit harder to ignore
     * compile errors :).
     *
     * @see Matcher for reasons why.
     */
    void _dont_implement_Matcher___instead_extend_BaseMatcher_();
}
