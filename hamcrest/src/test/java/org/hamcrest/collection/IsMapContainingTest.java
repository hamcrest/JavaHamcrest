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

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsMapContainingTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return IsMapContaining.hasEntry("irrelevant", "irrelevant");
    }

    public void testMatchesMapContainingMatchingKeyAndValue() {
        Map<String,Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);

        assertMatches("matcherA", hasEntry(equalTo("a"), equalTo(1)), map);
        assertMatches("matcherB", hasEntry(equalTo("b"), equalTo(2)), map);
        assertMismatchDescription("map was [<a=1>, <b=2>]", hasEntry(equalTo("c"), equalTo(3)), map);
    }

    @SuppressWarnings("unchecked")
    public void testMatchesMapContainingMatchingKeyAndValueWithoutGenerics() {
        Map map = new HashMap();
        map.put("a", 1);
        map.put("b", 2);

        assertMatches("matcherA", hasEntry(equalTo("a"), equalTo(1)), map);
        assertMatches("matcherB", hasEntry(equalTo("b"), equalTo(2)), map);
        assertFalse("matcherC", hasEntry(equalTo("c"), equalTo(3)).matches(map)); // working around generics problem
    }

    public void testDoesNotMatchNull() {
        assertMismatchDescription("was null", hasEntry(anything(), anything()), null);
    }

    public void testHasReadableDescription() {
        assertDescription("map containing [\"a\"-><2>]", hasEntry(equalTo("a"), (equalTo(2))));
    }
}
