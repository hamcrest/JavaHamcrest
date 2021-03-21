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

import static org.hamcrest.collection.IsMapContaining.hasValue;

public class IsMapContainingValueTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasValue("foo");
    }

    public void testHasReadableDescription() {
        assertDescription("map containing [ANYTHING->\"a\"]", hasValue("a"));
    }
    
    public void testDoesNotMatchEmptyMap() {
        Map<String,Integer> map = new HashMap<String,Integer>();
        assertMismatchDescription("map was []", hasValue(1), map);
    }
    
    public void testMatchesSingletonMapContainingValue() {
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("a", 1);
        
        assertMatches("Singleton map", hasValue(1), map);
    }

    public void testMatchesMapContainingValue() {
        Map<String,Integer> map = new TreeMap<String,Integer>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        
        assertMatches("hasValue 1", hasValue(1), map);      
        assertMatches("hasValue 3", hasValue(3), map);      
        assertMismatchDescription("map was [<a=1>, <b=2>, <c=3>]", hasValue(4), map);      
    }
}
