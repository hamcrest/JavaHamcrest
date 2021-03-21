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

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.collection.ArrayMatching.hasItemInArray;

public class HasItemInArrayTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasItemInArray("irrelevant");
    }

    public void testMatchesAnArrayThatContainsAnElementMatchingTheGivenMatcher() {
        assertMatches("should matches array that contains 'a'",
                hasItemInArray("a"), new String[]{"a", "b", "c"});
    }

    public void testDoesNotMatchAnArrayThatDoesntContainAnElementMatchingTheGivenMatcher() {
        assertDoesNotMatch("should not matches array that doesn't contain 'a'",
                hasItemInArray("a"), new String[]{"b", "c"});
        assertDoesNotMatch("should not matches empty array",
                hasItemInArray("a"), new String[0]);
        assertMismatchDescription(
              "mismatches were: [<3> was greater than <2>, <4> was greater than <2>, <5> was greater than <2>]",
              hasItemInArray(lessThan(2)), new Integer[] {3, 4, 5});
    }

    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not matches null",
                hasItemInArray("a"), null);
    }

    public void testHasAReadableDescription() {
        assertDescription("an array containing a value less than <2>", hasItemInArray(lessThan(2)));
    }

    // Remaining code no longer compiles, thanks to generics. I think that's a good thing, but
    // I still need to investigate how this behaves with code that doesn't use generics.
    // I expect ClassCastExceptions will be thrown.
    // -Joe.

//    public void testDoesNotMatchObjectThatIsNotAnArray() {
//        assertDoesNotMatch("should not matches empty list",
//                arrayContaining("a"), "not a collection");
//    }

//    public void testMatchesPrimitiveArrayElements() {
//        assertMatches("boolean", arrayContaining(true), new boolean[]{true, false});
//        assertDoesNotMatch("boolean", arrayContaining(false), new boolean[]{false});
//
//        assertMatches("byte", arrayContaining((byte) 1), new byte[]{1, 2, 3});
//        assertDoesNotMatch("byte", arrayContaining((byte) 0), new byte[]{1, 2, 3});
//
//        assertMatches("char", arrayContaining('a'), new char[]{'a', 'b', 'c'});
//        assertDoesNotMatch("char", arrayContaining('z'), new char[]{'a', 'b', 'c'});
//
//        assertMatches("short", arrayContaining((short) 1), new short[]{1, 2, 3});
//        assertDoesNotMatch("short", arrayContaining((short) 0), new short[]{1, 2, 3});
//
//        assertMatches("int", arrayContaining(1), new int[]{1, 2, 3});
//        assertDoesNotMatch("int", arrayContaining(0), new int[]{1, 2, 3});
//
//        assertMatches("long", arrayContaining(1L), new long[]{1, 2, 3});
//        assertDoesNotMatch("long", arrayContaining(0L), new long[]{1, 2, 3});
//
//        assertMatches("float", arrayContaining(1f), new float[]{1f, 2f, 3f});
//        assertDoesNotMatch("float", arrayContaining(0f), new float[]{1f, 2f, 3f});
//
//        assertMatches("double", arrayContaining(1.0), new double[]{1.0, 2.0, 3.0});
//        assertDoesNotMatch("double", arrayContaining(0.0), new double[]{1.0, 2.0, 3.0});
//    }

}
