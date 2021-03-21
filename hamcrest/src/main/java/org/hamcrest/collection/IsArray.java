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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;

/**
 * Matcher for array whose elements satisfy a sequence of matchers.
 * The array size must equal the number of element matchers.
 */
public class IsArray<T> extends TypeSafeMatcher<T[]> {
    private final Matcher<? super T>[] elementMatchers;
    
    public IsArray(Matcher<? super T>[] elementMatchers) {
        this.elementMatchers = elementMatchers.clone();
    }
    
    @Override
    public boolean matchesSafely(T[] array) {
        if (array.length != elementMatchers.length) return false;
        
        for (int i = 0; i < array.length; i++) {
            if (!elementMatchers[i].matches(array[i])) return false;
        }
        
        return true;
    }

    @Override
    public void describeMismatchSafely(T[] actual, Description mismatchDescription) {
        if (actual.length != elementMatchers.length) {
            mismatchDescription.appendText("array length was ").appendValue(actual.length);
            return;
        }
        for (int i = 0; i < actual.length; i++) {
            if (!elementMatchers[i].matches(actual[i])) {
                mismatchDescription.appendText("element ").appendValue(i).appendText(" ");
                elementMatchers[i].describeMismatch(actual[i], mismatchDescription);
                return;
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void describeTo(Description description) {
        description.appendList(descriptionStart(), descriptionSeparator(), descriptionEnd(), 
                               Arrays.asList(elementMatchers));
    }
    
    /**
     * Returns the string that starts the description.
     * 
     * Can be overridden in subclasses to customise how the matcher is
     * described.
     */
    protected String descriptionStart() {
        return "[";
    }

    /**
     * Returns the string that separates the elements in the description.
     * 
     * Can be overridden in subclasses to customise how the matcher is
     * described.
     */
    protected String descriptionSeparator() {
        return ", ";
    }

    /**
     * Returns the string that ends the description.
     * 
     * Can be overridden in subclasses to customise how the matcher is
     * described.
     */
    protected String descriptionEnd() {
        return "]";
    }
    
    /**
     * Creates a matcher that matches arrays whose elements are satisfied by the specified matchers.  Matches
     * positively only if the number of matchers specified is equal to the length of the examined array and
     * each matcher[i] is satisfied by array[i].
     * For example:
     * <pre>assertThat(new Integer[]{1,2,3}, is(array(equalTo(1), equalTo(2), equalTo(3))))</pre>
     * 
     * @param elementMatchers
     *     the matchers that the elements of examined arrays should satisfy
     */
    public static <T> IsArray<T> array(Matcher<? super T>... elementMatchers) {
        return new IsArray<T>(elementMatchers);
    }

}
