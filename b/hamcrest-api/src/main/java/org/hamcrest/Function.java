/*  Copyright (c) 2000-2006 hamcrest.org
 */

package org.hamcrest;

/**
 * A function is used to retrieve a value from the given input, it's main purpose is to allow
 * the implementation of second-order matchers.
 * A function is able to describe itself, so it can be used by matchers when giving fail feedback.
 *
 * @see ExtractMatcher
 */
public interface Function<Input, Output> extends SelfDescribing {
    Output apply (Input value);
}
