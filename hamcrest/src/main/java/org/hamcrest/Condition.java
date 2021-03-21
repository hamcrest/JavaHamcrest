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
 * A Condition implements part of a multi-step match. We sometimes need to write matchers
 * that have a sequence of steps, where each step depends on the result of the previous
 * step and we can stop processing as soon as a step fails. These classes provide
 * infrastructure for writing such a sequence.
 *
 * Based on https://github.com/npryce/maybe-java
 * @author Steve Freeman 2012 http://www.hamcrest.com
 */

public abstract class Condition<T> {
    public static final NotMatched<Object> NOT_MATCHED = new NotMatched<Object>();

    public interface Step<I, O> {
        Condition<O> apply(I value, Description mismatch);
    }

    private Condition() { }

    public abstract boolean matching(Matcher<T> match, String message);
    public abstract <U> Condition<U> and(Step<? super T, U> mapping);

    public final boolean matching(Matcher<T> match) { return matching(match, ""); }
    public final <U> Condition<U> then(Step<? super T, U> mapping) { return and(mapping); }

    @SuppressWarnings("unchecked")
    public static <T> Condition<T> notMatched() {
        return (Condition<T>) NOT_MATCHED;
    }

    public static <T> Condition<T> matched(final T theValue, final Description mismatch) {
        return new Matched<T>(theValue, mismatch);
    }

    private static final class Matched<T> extends Condition<T> {
        private final T theValue;
        private final Description mismatch;

        private Matched(T theValue, Description mismatch) {
            this.theValue = theValue;
            this.mismatch = mismatch;
        }

        @Override
        public boolean matching(Matcher<T> matcher, String message) {
            if (matcher.matches(theValue)) {
                return true;
            }
            mismatch.appendText(message);
            matcher.describeMismatch(theValue, mismatch);
            return false;
        }

        @Override
        public <U> Condition<U> and(Step<? super T, U> next) {
            return next.apply(theValue, mismatch);
        }
    }

    private static final class NotMatched<T> extends Condition<T> {
        @Override public boolean matching(Matcher<T> match, String message) { return false; }

        @Override public <U> Condition<U> and(Step<? super T, U> mapping) {
            return notMatched();
        }
    }
}
