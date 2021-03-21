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

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;

public class IsEmptyIterableTest extends AbstractMatcherTest {

    @Override
    protected Matcher<Iterable<?>> createMatcher() {
        return emptyIterable();
    }

    public void testMatchesAnEmptyIterable() {
        assertMatches("empty iterable", createMatcher(), emptyCollection());
    }

    public void testDoesNotMatchAnIterableWithItems() {
        assertDoesNotMatch("iterable with an item", createMatcher(), collectionOfValues());
    }

    public void testHasAReadableDescription() {
        assertDescription("an empty iterable", createMatcher());
    }

    public void testCompiles() {
        needs(IsEmptyIterable.emptyIterableOf(String.class));
    }

    private void needs(@SuppressWarnings("unused") Matcher<Iterable<String>> bar) { }

    private static Collection<String> collectionOfValues() {
        return new ArrayList<String>(asList("one", "three"));
    }

    private static Collection<Integer> emptyCollection() {
        return new ArrayList<Integer>();
    }
}
