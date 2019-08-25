/**
 * BSD License
 *
 * Copyright (c) 2000-2015 www.hamcrest.org
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

import java.util.Arrays;
import java.util.Collections;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;

/**
 * Is iterable with size test.
 */
public class IsIterableWithSizeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return iterableWithSize(7);
    }

    public void testMatchesEmptyIterable() throws Exception {
        assertMatches("Empty iterable", iterableWithSize(0), Collections.emptyList());
    }

    public void testMatchingSingleItemIterable() throws Exception {
        assertMatches("Single item iterable", iterableWithSize(1), Arrays.<Object>asList(1));
    }

    public void testMatchingMultipleItemIterable() throws Exception {
        assertMatches("Multiple item iterable", iterableWithSize(3), Arrays.<Object>asList(1, 2, 3));
    }

    public void testDoesNotMatchIncorrectSize() throws Exception {
        assertDoesNotMatch("Incorrect size", iterableWithSize(3), Arrays.<Object>asList(1));
    }

    public void testHasAReadableDescription() {
        assertDescription("an iterable with size <4>", iterableWithSize(4));
    }
}
