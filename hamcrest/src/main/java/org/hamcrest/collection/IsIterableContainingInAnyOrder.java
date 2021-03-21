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
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;

public class IsIterableContainingInAnyOrder<T> extends TypeSafeDiagnosingMatcher<Iterable<? extends T>> {
    private final Collection<Matcher<? super T>> matchers;

    public IsIterableContainingInAnyOrder(Collection<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }
    
    @Override
    protected boolean matchesSafely(Iterable<? extends T> items, Description mismatchDescription) {
      final Matching<T> matching = new Matching<>(matchers, mismatchDescription);
      for (T item : items) {
        if (! matching.matches(item)) {
          return false;
        }
      }
      
      return matching.isFinished(items);
    }
    
    @Override
    public void describeTo(Description description) {
      description.appendText("iterable with items ")
          .appendList("[", ", ", "]", matchers)
          .appendText(" in any order");
    }

    private static class Matching<S> {
      private final Collection<Matcher<? super S>> matchers;
      private final Description mismatchDescription;

      public Matching(Collection<Matcher<? super S>> matchers, Description mismatchDescription) {
        this.matchers = new ArrayList<>(matchers);
        this.mismatchDescription = mismatchDescription;
      }
      
      public boolean matches(S item) {
        if (matchers.isEmpty()) {
          mismatchDescription.appendText("no match for: ").appendValue(item);
          return false;
        }
        return isMatched(item);
      }

      public boolean isFinished(Iterable<? extends S> items) {
        if (matchers.isEmpty()) {
          return true;
        }
        mismatchDescription
          .appendText("no item matches: ").appendList("", ", ", "", matchers)
          .appendText(" in ").appendValueList("[", ", ", "]", items);
        return false;
      }

      private boolean isMatched(S item) {
        for (Matcher<? super S>  matcher : matchers) {
          if (matcher.matches(item)) {
            matchers.remove(matcher);
            return true;
          }
        }
        mismatchDescription.appendText("not matched: ").appendValue(item);
        return false;
      }
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
     * in the specified matchers.  For a positive match, the examined iterable must be of the same
     * length as the number of specified matchers.
     * </p>
     * <p>
     * N.B. each of the specified matchers will only be used once during a given examination, so be
     * careful when specifying matchers that may be satisfied by more than one entry in an examined
     * iterable.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(equalTo("bar"), equalTo("foo")))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... itemMatchers) {
        return containsInAnyOrder(Arrays.asList(itemMatchers));
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each logically equal to one item
     * anywhere in the specified items. For a positive match, the examined iterable
     * must be of the same length as the number of specified items.
     * </p>
     * <p>
     * N.B. each of the specified items will only be used once during a given examination, so be
     * careful when specifying items that may be equal to more than one entry in an examined
     * iterable.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder("bar", "foo"))</pre>
     * 
     * @param items
     *     the items that must equal the items provided by an examined {@link Iterable} in any order
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... items) {
        List<Matcher<? super T>> matchers = new ArrayList<>();
        for (T item : items) {
            matchers.add(equalTo(item));
        }
        
        return new IsIterableContainingInAnyOrder<>(matchers);
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
     * in the specified collection of matchers.  For a positive match, the examined iterable
     * must be of the same length as the specified collection of matchers.
     * </p>
     * <p>
     * N.B. each matcher in the specified collection will only be used once during a given
     * examination, so be careful when specifying matchers that may be satisfied by more than
     * one entry in an examined iterable.
     * </p>
     * <p>For example:</p>
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(Arrays.asList(equalTo("bar"), equalTo("foo"))))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     */
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> itemMatchers) {
        return new IsIterableContainingInAnyOrder<>(itemMatchers);
    }
}

