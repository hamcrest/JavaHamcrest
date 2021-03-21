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
import org.hamcrest.MatcherAssert;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.hamcrest.core.IsEqual.equalTo;

public final class IsMapWithSizeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return aMapWithSize(7);
    }

    public void testMatchesWhenSizeIsCorrect() {
        assertMatches("correct size", aMapWithSize(equalTo(2)), mapWithKeys("a", "b"));
        assertMismatchDescription("map size was <3>", aMapWithSize(equalTo(2)), mapWithKeys("a", "b", "c"));
    }

    public void testMatchesMapWhenSizeIsCorrectUsingObjectElementType() {
        Map<Object, Object> map = mapWithKeys(new Object(), new Object());
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertMismatchDescription("map size was <2>", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesMapWhenSizeIsCorrectUsingStringElementType() {
        Map<String, Integer> map = mapWithKeys("a", "b");
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertMismatchDescription("map size was <2>", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesMapWhenSizeIsCorrectUsingWildcardElementType() {
        Map<?, ?> map = mapWithKeys("a", "b");
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertMismatchDescription("map size was <2>", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesListWhenSizeIsCorrectUsingObjectElementType() {
        Map<Object, Object> map = mapWithKeys(new Object(), new Object());
        assertMatches("correct size", aMapWithSize(equalTo(2)), map);
        assertMismatchDescription("map size was <2>", aMapWithSize(equalTo(3)), map);
    }

    public void testMatchesListWhenSizeIsCorrectUsingStringElementType() {
        Map<String, Integer> list = mapWithKeys("a", "b");
        assertMatches("correct size", aMapWithSize(equalTo(2)), list);
        assertMismatchDescription("map size was <2>", aMapWithSize(equalTo(3)), list);
    }

    public void testMatchesListWhenSizeIsCorrectUsingWildcardElementType() {
        Map<?, ?> list = mapWithKeys("a", "b");
        assertMatches("correct size", aMapWithSize(equalTo(2)), list);
        assertMismatchDescription("map size was <2>", aMapWithSize(equalTo(3)), list);
    }

    public void testProvidesConvenientShortcutForHasSizeEqualTo() {
        assertMatches("correct size", aMapWithSize(2), mapWithKeys(new Object(), new Object()));
        assertMismatchDescription("map size was <3>", aMapWithSize(2), mapWithKeys(new Object(), new Object(), new Object()));
    }

    public void testHasAReadableDescription() {
        assertDescription("a map with size <3>", aMapWithSize(equalTo(3)));
    }
    
    public void testCompilesWithATypedMap() {
      Map<String, Integer> arrayList = new HashMap<String, Integer>();
      MatcherAssert.assertThat(arrayList, aMapWithSize(0));
    }
    
    private static <K, V> Map<K, V> mapWithKeys(K... keys) {
        final Map<K, V> result = new HashMap<K, V>();
        for (K key : keys) {
            result.put(key, null);
        }
        return result;
    }
}
