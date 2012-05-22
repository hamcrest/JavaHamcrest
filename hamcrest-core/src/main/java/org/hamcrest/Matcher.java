/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;

/**
 * A matcher over acceptable values.
 * A matcher is able to describe itself to give feedback when it fails.
 * <p/>
 * Matcher implementations should <b>NOT directly implement this interface</b>.
 * Instead, <b>extend</b> the {@link BaseMatcher} abstract class,
 * which will ensure that the Matcher API can grow to support
 * new features and remain compatible with all Matcher implementations.
 * <p/>
 * For easy access to common Matcher implementations, use the static factory
 * methods in {@link CoreMatchers}.
 * <p/>
 * N.B. Well designed matchers should be immutable.
 * 
 * @see CoreMatchers
 * @see BaseMatcher
 */
public interface Matcher<T> extends SelfDescribing {

    /**
     * Evaluates the matcher for argument <var>item</var>.
     * <p/>
     * This method matches against Object, instead of the generic type T. This is
     * because the caller of the Matcher does not know at runtime what the type is
     * (because of type erasure with Java generics). It is down to the implementations
     * to check the correct type.
     *
     * @param item the object against which the matcher is evaluated.
     * @return <code>true</code> if <var>item</var> matches, otherwise <code>false</code>.
     *
     * @see BaseMatcher
     */
    boolean matches(Object item);
    
    /**
     * Generate a description of why the matcher has not accepted the item.
     * The description will be part of a larger description of why a matching
     * failed, so it should be concise. 
     * This method assumes that <code>matches(item)</code> is false, but 
     * will not check this.
     *
     * @param item The item that the Matcher has rejected.
     * @param mismatchDescription
     *     The description to be built or appended to.
     */
    void describeMismatch(Object item, Description mismatchDescription);

    /**
     * This method simply acts a friendly reminder not to implement Matcher directly and
     * instead extend BaseMatcher. It's easy to ignore JavaDoc, but a bit harder to ignore
     * compile errors .
     *
     * @see Matcher for reasons why.
     * @see BaseMatcher
     * @deprecated to make
     */
    @Deprecated
    void _dont_implement_Matcher___instead_extend_BaseMatcher_();
}
