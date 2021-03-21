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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.Collection;

public class IsIn<T> extends BaseMatcher<T> {
    private final Collection<T> collection;

    public IsIn(Collection<T> collection) {
        this.collection = collection;
    }
    
    public IsIn(T[] elements) {
        collection = Arrays.asList(elements);
    }
    
    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean matches(Object o) {
        return collection.contains(o);
    }

    @Override
    public void describeTo(Description buffer) {
        buffer.appendText("one of ");
        buffer.appendValueList("{", ", ", "}", collection);
    }
    
    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified collection.
     * For example:
     * <pre>assertThat("foo", isIn(Arrays.asList("bar", "foo")))</pre>
     * 
     * @deprecated use is(in(...)) instead
     * 
     * @param collection
     *     the collection in which matching items must be found
     * 
     */
    @Deprecated
    public static <T> Matcher<T> isIn(Collection<T> collection) {
        return in(collection);
    }
    
    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified collection.
     * For example:
     * <pre>assertThat("foo", is(in(Arrays.asList("bar", "foo"))))</pre>
     * 
     * @param collection
     *     the collection in which matching items must be found
     * 
     */
    public static <T> Matcher<T> in(Collection<T> collection) {
        return new IsIn<>(collection);
    }

    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified array.
     * For example:
     * <pre>assertThat("foo", isIn(new String[]{"bar", "foo"}))</pre>
     * 
     * @deprecated use is(in(...)) instead
     * 
     * @param elements
     *     the array in which matching items must be found
     * 
     */
    @Deprecated
    public static <T> Matcher<T> isIn(T[] elements) {
        return in(elements);
    }
    
    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified array.
     * For example:
     * <pre>assertThat("foo", is(in(new String[]{"bar", "foo"})))</pre>
     * 
     * @param elements
     *     the array in which matching items must be found
     * 
     */
    public static <T> Matcher<T> in(T[] elements) {
        return new IsIn<>(elements);
    }
    
    /**
     * Creates a matcher that matches when the examined object is equal to one of the
     * specified elements.
     * For example:
     * <pre>assertThat("foo", isOneOf("bar", "foo"))</pre>
     * 
     * @deprecated use is(oneOf(...)) instead
     * 
     * @param elements
     *     the elements amongst which matching items will be found 
     * 
     */
    @SafeVarargs
    @Deprecated
    public static <T> Matcher<T> isOneOf(T... elements) {
        return oneOf(elements);
    }
    
    /**
     * Creates a matcher that matches when the examined object is equal to one of the
     * specified elements.
     * For example:
     * <pre>assertThat("foo", is(oneOf("bar", "foo")))</pre>
     *  
     * @param elements
     *     the elements amongst which matching items will be found 
     * 
     */
    @SafeVarargs
    public static <T> Matcher<T> oneOf(T... elements) {
        return in(elements);
    }
}
