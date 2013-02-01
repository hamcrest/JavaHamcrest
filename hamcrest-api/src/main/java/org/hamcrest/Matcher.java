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
     * Generates a description of the matcher. The description may be part of a 
     * larger description, so it should be concise. The description should be able to
     * replace X in the sentence "Expected X," for example, "Expected <U>null</U>."
     *
     * @param description
     *     The description to be built or appended to.
     */
    @Override
    void describeTo(Description description);

    /**
     * Generate a description of the given item from the Matcher's point of view.
     * The description will be part of a larger description of why a matching
     * failed, so it should be concise.
     * The description should be able to replace Y in the sentence "Expected X but Y," and
     * should be in the past tense, for example, "Expected null but <U>was &lt;"foo"&gt;</U>."
     * The description should NOT describe the matcher, but rather should highlight features of interest on the item as they actually are.
     * The description must ALWAYS be filled in, regardless of return value of {@link #matches}. 
     *
     * @param item The item that the Matcher is considering.
     * @param mismatchDescription
     *     The description to be built or appended to.
     */
    void describeMismatch(Object item, Description mismatchDescription);

    /**
     * Returns the best estimate for the type parameter T of this
     * matcher (usually the type of parameter this matcher is expected
     * to match against), or null if no reasonable estimate can be made.
     */
    Class<T> getParameterType();

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
