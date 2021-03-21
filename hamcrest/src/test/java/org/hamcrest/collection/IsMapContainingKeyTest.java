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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class IsMapContainingKeyTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasKey("foo");
    }

    public void testMatchesSingletonMapContainingKey() {
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        
        assertMatches("Matches single key", hasKey("a"), map);
    }
    
    public void testMatchesMapContainingKey() {
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        
        assertMatches("Matches a", hasKey("a"), map);
        assertMatches("Matches c", hasKey("c"), map);
    }
    

//    No longer compiles
//    public void testMatchesMapContainingKeyWithNoGenerics() {
//        Map map = new HashMap();
//        map.put("a", 1);
//        map.put("b", 2);
//        map.put("c", 3);
//
//        assertMatches("Matches a", hasKey("a"), map);
//        assertMatches("Matches c", hasKey("c"), map);
//    }

    public void testMatchesMapContainingKeyWithIntegerKeys() throws Exception {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "A");
        map.put(2, "B");

        assertThat(map, hasKey(1));
    }

    public void testMatchesMapContainingKeyWithNumberKeys() throws Exception {
        Map<Number, String> map = new HashMap<Number, String>();
        map.put(1, "A");
        map.put(2, "B");

        assertThat(map, hasKey((Number)1));

        // TODO: work out the correct sprinkling of wildcards to get this to work!
//        assertThat(map, hasKey(1));
    }

    public void testHasReadableDescription() {
        assertDescription("map containing [\"a\"->ANYTHING]", hasKey("a"));
    }
    
    public void testDoesNotMatchEmptyMap() {
        assertMismatchDescription("map was []", hasKey("Foo"), new HashMap<String,Integer>());
    }
    
    public void testDoesNotMatchMapMissingKey() {
        Map<String,Integer> map = new TreeMap<String, Integer>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        
        assertMismatchDescription("map was [<a=1>, <b=2>, <c=3>]", hasKey("d"), map);
    }
}
