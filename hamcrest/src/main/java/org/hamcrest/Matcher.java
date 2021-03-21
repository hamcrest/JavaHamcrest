/**
 * BSD License
 *
 * Copyright (c) 2000-2021 www.hamcrest.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * Neither the name of Hamcrest nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hamcrest;

/**
 * <p>
 * A matcher over acceptable values.
 * A matcher is able to describe itself to give feedback when it fails.
 * </p>
 * <p>
 * Matcher implementations should <b>NOT directly implement this interface</b>.
 * Instead, <b>extend</b> the {@link BaseMatcher} abstract class,
 * which will ensure that the Matcher API can grow to support
 * new features and remain compatible with all Matcher implementations.
 * </p>
 * <p>
 * When using Hamcrest, there is no guarantee as to how often <code>matches()</code> or
 * <code>describeMismatch()</code> will be called, so the objects passed as
 * <code>actual</code> arguments should not change when referenced. If you're testing a
 * stream, a good practice is to collect the contents of the stream before matching.
 * </p>
 * <p>
 * N.B. Well designed matchers should be immutable.
 * </p>
 *
 * @see BaseMatcher
 */
public interface Matcher<T> extends SelfDescribing {

    /**
     * Evaluates the matcher for argument <var>item</var>.
     *
     * This method matches against Object, instead of the generic type T. This is
     * because the caller of the Matcher does not know at runtime what the type is
     * (because of type erasure with Java generics). It is down to the implementations
     * to check the correct type.
     *
     * @param actual the object against which the matcher is evaluated.
     * @return <code>true</code> if <var>item</var> matches, otherwise <code>false</code>.
     *
     * @see BaseMatcher
     */
    boolean matches(Object actual);
    
    /**
     * Generate a description of why the matcher has not accepted the item.
     * The description will be part of a larger description of why a matching
     * failed, so it should be concise. 
     * This method assumes that <code>matches(item)</code> is false, but 
     * will not check this.
     *
     * @param actual The item that the Matcher has rejected.
     * @param mismatchDescription
     *     The description to be built or appended to.
     */
    void describeMismatch(Object actual, Description mismatchDescription);

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
