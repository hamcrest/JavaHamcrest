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

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsCollectionContainingTest extends AbstractMatcherTest {
    @Override
    protected Matcher<?> createMatcher() {
        return hasItem(equalTo("irrelevant"));
    }

    public void testMatchesACollectionThatContainsAnElementMatchingTheGivenMatcher() {
        Matcher<Iterable<? super String>> itemMatcher = hasItem(equalTo("a"));
        
        assertMatches("should match list that contains 'a'",
                itemMatcher, asList("a", "b", "c"));
    }

    public void testDoesNotMatchCollectionThatDoesntContainAnElementMatchingTheGivenMatcher() {
        final Matcher<Iterable<? super String>> matcher1 = hasItem(mismatchable("a"));
        assertMismatchDescription("mismatches were: [mismatched: b, mismatched: c]", matcher1, asList("b", "c"));
        
        
        final Matcher<Iterable<? super String>> matcher2 = hasItem(equalTo("a"));
        assertMismatchDescription("was empty", matcher2, new ArrayList<String>());
    }

    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not matches null", hasItem(equalTo("a")), null);
    }

    public void testHasAReadableDescription() {
        assertDescription("a collection containing \"a\"", hasItem(equalTo("a")));
    }
    
    public void testCanMatchItemWhenCollectionHoldsSuperclass() // Issue 24
    {
      final Set<Number> s = new HashSet<Number>();
      s.add(Integer.valueOf(2));
      assertThat(s, new IsCollectionContaining<Number>(new IsEqual<Number>(Integer.valueOf(2))));
      assertThat(s, IsCollectionContaining.hasItem(Integer.valueOf(2)));
    }

    @SuppressWarnings("unchecked")
    public void testMatchesAllItemsInCollection() {
        final Matcher<Iterable<String>> matcher1 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("should match list containing all items",
                matcher1,
                asList("a", "b", "c"));
        
        final Matcher<Iterable<String>> matcher2 = hasItems("a", "b", "c");
        assertMatches("should match list containing all items (without matchers)",
                matcher2,
                asList("a", "b", "c"));
        
        final Matcher<Iterable<String>> matcher3 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("should match list containing all items in any order",
                matcher3,
                asList("c", "b", "a"));
        
        final Matcher<Iterable<String>> matcher4 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("should match list containing all items plus others",
                matcher4,
                asList("e", "c", "b", "a", "d"));
        
        final Matcher<Iterable<String>> matcher5 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertDoesNotMatch("should not match list unless it contains all items",
                matcher5,
                asList("e", "c", "b", "d")); // 'a' missing
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

