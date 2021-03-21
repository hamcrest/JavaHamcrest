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
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.core.IsIterableContaining.hasItem;
import static org.hamcrest.core.IsIterableContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

public final class IsIterableContainingTest {
    
    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<?> matcher = hasItem(equalTo("irrelevant"));
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    matchesACollectionThatContainsAnElementForTheGivenMatcher() {
        final Matcher<Iterable<? super String>> itemMatcher = hasItem(equalTo("a"));

        assertMatches("list containing 'a'", itemMatcher, asList("a", "b", "c"));
    }

    @Test public void
    doesNotMatchCollectionWithoutAnElementForGivenMatcher() {
        final Matcher<Iterable<? super String>> matcher = hasItem(mismatchable("a"));
        
        assertMismatchDescription("mismatches were: [mismatched: b, mismatched: c]", matcher, asList("b", "c"));
        assertMismatchDescription("was empty", matcher, new ArrayList<String>());
    }

    @Test public void
    doesNotMatchNull() {
        assertDoesNotMatch("doesn't match null", hasItem(equalTo("a")), null);
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("a collection containing mismatchable: a", hasItem(mismatchable("a")));
    }
    
    @Test public void
    canMatchItemWhenCollectionHoldsSuperclass() { // Issue 24
        final Set<Number> s = new HashSet<>();
        s.add(2);

        assertMatches(new IsIterableContaining<>(new IsEqual<Number>(2)), s);
        assertMatches(IsIterableContaining.hasItem(2), s);
    }

    @SuppressWarnings("unchecked")
    @Test public void
    matchesMultipleItemsInCollection() {
        final Matcher<Iterable<String>> matcher1 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("list containing all items", matcher1, asList("a", "b", "c"));
        
        final Matcher<Iterable<String>> matcher2 = hasItems("a", "b", "c");
        assertMatches("list containing all items (without matchers)", matcher2, asList("a", "b", "c"));
        
        final Matcher<Iterable<String>> matcher3 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("list containing all items in any order", matcher3, asList("c", "b", "a"));
        
        final Matcher<Iterable<String>> matcher4 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("list containing all items plus others", matcher4, asList("e", "c", "b", "a", "d"));
        
        final Matcher<Iterable<String>> matcher5 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertDoesNotMatch("not match list unless it contains all items", matcher5, asList("e", "c", "b", "d")); // 'a' missing
    }
    
    @Test public void
    reportsMismatchWithAReadableDescriptionForMultipleItems() {
        final Matcher<Iterable<Integer>> matcher = hasItems(3, 4);
        
        assertMismatchDescription("a collection containing <4> mismatches were: [was <1>, was <2>, was <3>]",
                                  matcher, asList(1, 2, 3));
    }

    private static Matcher<? super String> mismatchable(final String string) {
        return new TypeSafeDiagnosingMatcher<String>() {
            @Override
            protected boolean matchesSafely(String item, Description mismatchDescription) {
                if (string.equals(item)) 
                    return true;

                mismatchDescription.appendText("mismatched: " + item);
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("mismatchable: " + string);
            }
        };
    }
}

