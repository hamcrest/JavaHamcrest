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
import org.hamcrest.collection.IsIterableContainingInOrderTest.WithValue;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrderTest.make;
import static org.hamcrest.collection.IsIterableContainingInOrderTest.value;

public class IsIterableContainingInAnyOrderTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return containsInAnyOrder(1, 2);
    }   

    public void testMatchesSingleItemIterable() {
      assertMatches("single item", containsInAnyOrder(1), asList(1));
    }

    public void testDoesNotMatchEmpty() {
        assertMismatchDescription("no item matches: <1>, <2> in []", containsInAnyOrder(1, 2), Collections.<Integer>emptyList());
    }
    
    public void testMatchesIterableOutOfOrder() {
        assertMatches("Out of order", containsInAnyOrder(1, 2), asList(2, 1));
    }
    
    public void testMatchesIterableInOrder() {
        assertMatches("In order", containsInAnyOrder(1, 2), asList(1, 2));
    }
    
    public void testDoesNotMatchIfOneOfMultipleElementsMismatches() {
        assertMismatchDescription("not matched: <4>", containsInAnyOrder(1, 2, 3), asList(1, 2, 4));
    }

    @SuppressWarnings("unchecked")
    public void testDoesNotMatchIfThereAreMoreElementsThanMatchers() {
        final Matcher<Iterable<? extends WithValue>> helpTheCompilerOut = containsInAnyOrder(value(1), value(3));
        assertMismatchDescription("not matched: <WithValue 2>", helpTheCompilerOut, asList(make(1), make(2), make(3)));
    }
    
    public void testDoesNotMatchIfThereAreMoreMatchersThanElements() {
        assertMismatchDescription("no item matches: <4> in [<1>, <2>, <3>]", containsInAnyOrder(1, 2, 3, 4), asList(1, 2, 3));
    }

    public void testHasAReadableDescription() {
        assertDescription("iterable with items [<1>, <2>] in any order", containsInAnyOrder(1, 2));
    }
}
