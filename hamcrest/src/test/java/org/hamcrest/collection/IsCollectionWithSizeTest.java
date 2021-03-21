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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsCollectionWithSizeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasSize(7);
    }

    public void testMatchesWhenSizeIsCorrect() {
        assertMatches("correct size", hasSize(equalTo(2)), asList(null, null));
        assertMismatchDescription("collection size was <3>", hasSize(equalTo(2)), asList(null, null, null));
    }

    public void testMatchesCollectionWhenSizeIsCorrectUsingObjectElementType() {
        Collection<Object> list = asList(null, null);
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    public void testMatchesCollectionWhenSizeIsCorrectUsingStringElementType() {
        Collection<String> list = asList("a", "b");
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    public void testMatchesCollectionWhenSizeIsCorrectUsingWildcardElementType() {
        Collection<?> list = asList("a", "b");
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    public void testMatchesListWhenSizeIsCorrectUsingObjectElementType() {
        List<Object> list = asList(null, null);
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    public void testMatchesListWhenSizeIsCorrectUsingStringElementType() {
        List<String> list = asList("a", "b");
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    public void testMatchesListWhenSizeIsCorrectUsingWildcardElementType() {
        List<?> list = asList("a", "b");
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    public void testProvidesConvenientShortcutForHasSizeEqualTo() {
        assertMatches("correct size", hasSize(2), asList(null, null));
        assertMismatchDescription("collection size was <3>", hasSize(2), asList(null, null, null));
    }

    public void testHasAReadableDescription() {
        assertDescription("a collection with size <3>", hasSize(equalTo(3)));
    }
    
    public void testCompilesWithATypedCollection() {
      // To prove Issue 43
      ArrayList<String> arrayList = new ArrayList<String>();
      MatcherAssert.assertThat(arrayList, hasSize(0));
    }
}
