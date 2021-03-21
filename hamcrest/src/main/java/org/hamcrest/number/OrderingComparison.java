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
package org.hamcrest.number;

import org.hamcrest.Matcher;
import org.hamcrest.comparator.ComparatorMatcherBuilder;

public class OrderingComparison {

    private OrderingComparison() {
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * equal to the specified value, as reported by the <code>compareTo</code> method of the
     * <b>examined</b> object.
     * For example:
     * <pre>assertThat(1, comparesEqualTo(1))</pre>
     *
     * @param value the value which, when passed to the compareTo method of the examined object, should return zero
     */
    public static <T extends Comparable<T>> Matcher<T> comparesEqualTo(T value) {
        return ComparatorMatcherBuilder.<T>usingNaturalOrdering().comparesEqualTo(value);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * greater than the specified value, as reported by the <code>compareTo</code> method of the
     * <b>examined</b> object.
     * For example:
     * <pre>assertThat(2, greaterThan(1))</pre>
     *
     * @param value the value which, when passed to the compareTo method of the examined object, should return greater
     *              than zero
     */
    public static <T extends Comparable<T>> Matcher<T> greaterThan(T value) {
        return ComparatorMatcherBuilder.<T>usingNaturalOrdering().greaterThan(value);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * greater than or equal to the specified value, as reported by the <code>compareTo</code> method
     * of the <b>examined</b> object.
     * For example:
     * <pre>assertThat(1, greaterThanOrEqualTo(1))</pre>
     *
     * @param value the value which, when passed to the compareTo method of the examined object, should return greater
     *              than or equal to zero
     */
    public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T value) {
        return ComparatorMatcherBuilder.<T>usingNaturalOrdering().greaterThanOrEqualTo(value);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * less than the specified value, as reported by the <code>compareTo</code> method of the
     * <b>examined</b> object.
     * For example:
     * <pre>assertThat(1, lessThan(2))</pre>
     *
     * @param value the value which, when passed to the compareTo method of the examined object, should return less
     *              than zero
     */
    public static <T extends Comparable<T>> Matcher<T> lessThan(T value) {
        return ComparatorMatcherBuilder.<T>usingNaturalOrdering().lessThan(value);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * less than or equal to the specified value, as reported by the <code>compareTo</code> method
     * of the <b>examined</b> object.
     * For example:
     * <pre>assertThat(1, lessThanOrEqualTo(1))</pre>
     *
     * @param value the value which, when passed to the compareTo method of the examined object, should return less
     *              than or equal to zero
     */
    public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T value) {
        return ComparatorMatcherBuilder.<T>usingNaturalOrdering().lessThanOrEqualTo(value);
    }
}
