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
package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsEqual.equalTo;

@SuppressWarnings("unchecked")
public class IsIterableContainingInOrderTest extends AbstractMatcherTest {
    // temporary hack until the Java type system works
    private final Matcher<Iterable<? extends WithValue>> contains123 = contains(value(1), value(2), value(3));

    @Override
    protected Matcher<?> createMatcher() {
        return contains(1, 2);
    }

    public void testMatchingSingleItemIterable() throws Exception {
        assertMatches("Single item iterable", contains(1), singletonList(1));
    }

    public void testMatchingMultipleItemIterable() throws Exception {
        assertMatches("Multiple item iterable", contains(1, 2, 3), asList(1, 2, 3));
    }

    public void testDoesNotMatchWithMoreElementsThanExpected() throws Exception {
        assertMismatchDescription("not matched: <4>", contains(1, 2, 3), asList(1, 2, 3, 4));
    }

    public void testDoesNotMatchWithFewerElementsThanExpected() throws Exception {
        assertMismatchDescription("no item was value with <3>", contains123, asList(make(1), make(2)));
    }

    public void testDoesNotMatchIfSingleItemMismatches() throws Exception {
        assertMismatchDescription("item 0: value was <3>", contains(value(4)), singletonList(make(3)));
    }

    public void testDoesNotMatchIfOneOfMultipleItemsMismatch() throws Exception {
        assertMismatchDescription("item 2: value was <4>", contains123, asList(make(1), make(2), make(4)));
    }

    public void testDoesNotMatchEmptyIterable() throws Exception {
        assertMismatchDescription("no item was value with <4>", contains(value(4)), new ArrayList<WithValue>());
    }

    public void testHasAReadableDescription() {
        assertDescription("iterable containing [<1>, <2>]", contains(1, 2));
    }
    
    public void testCanHandleNullMatchers() {
        assertMatches(contains(null, null), asList(null, null));
    }

    public static class WithValue {
      private final int value;
      public WithValue(int value) { this.value = value; }
      public int getValue() { return value; }
      @Override public String toString() { return "WithValue " + value; }
    }

    public static WithValue make(int value) {
      return new WithValue(value);
    }

    public static Matcher<WithValue> value(int value) {
      return new FeatureMatcher<WithValue, Integer>(equalTo(value), "value with", "value") {
        @Override protected Integer featureValueOf(WithValue actual) { return actual.getValue(); }
      };
    }
}
