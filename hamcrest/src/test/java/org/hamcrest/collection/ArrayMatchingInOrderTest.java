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
import org.hamcrest.Matcher;

import static org.hamcrest.collection.ArrayMatching.arrayContaining;
import static org.hamcrest.core.IsEqual.equalTo;

public class ArrayMatchingInOrderTest extends AbstractMatcherTest {

    @SuppressWarnings("unchecked")
    @Override
    protected Matcher<?> createMatcher() {
        return arrayContaining(equalTo(1), equalTo(2));
    }

    @SuppressWarnings("unchecked")
    public void testHasAReadableDescription() {
        assertDescription("[<1>, <2>]", arrayContaining(equalTo(1), equalTo(2)));
    }
    
    public void testMatchesItemsInOrder() {
      assertMatches("in order", arrayContaining(1, 2, 3), new Integer[] {1, 2, 3});
      assertMatches("single", arrayContaining(1), new Integer[] {1});
    }

    @SuppressWarnings("unchecked")
    public void testAppliesMatchersInOrder() {
      assertMatches("in order", arrayContaining(equalTo(1), equalTo(2), equalTo(3)), new Integer[] {1, 2, 3});
      assertMatches("single", arrayContaining(equalTo(1)), new Integer[] {1});
    }
    
    public void testMismatchesItemsInOrder() {
      Matcher<Integer[]> matcher = arrayContaining(1, 2, 3);
      assertMismatchDescription("was null", matcher, null);
      assertMismatchDescription("no item was <1>", matcher, new Integer[] {});
      assertMismatchDescription("no item was <2>", matcher, new Integer[] {1});
      assertMismatchDescription("item 0: was <4>", matcher, new Integer[] {4,3,2,1});
      assertMismatchDescription("item 2: was <4>", matcher, new Integer[] {1,2, 4});
    }

    public void testCanHandleNullValuesInAnArray() {
      assertMatches("with nulls", arrayContaining(null, null), new Object[]{null, null});
    }
}
