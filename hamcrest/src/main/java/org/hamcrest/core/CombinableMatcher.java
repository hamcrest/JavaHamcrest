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

import java.util.ArrayList;

public class CombinableMatcher<T> extends TypeSafeDiagnosingMatcher<T> {
  private final Matcher<? super T> matcher;

  public CombinableMatcher(Matcher<? super T> matcher) {
    this.matcher = matcher;
  }

  @Override
  protected boolean matchesSafely(T item, Description mismatch) {
    if (!matcher.matches(item)) {
      matcher.describeMismatch(item, mismatch);
      return false;
    }
    return true;
  }

  @Override
  public void describeTo(Description description) {
    description.appendDescriptionOf(matcher);
  }

  public CombinableMatcher<T> and(Matcher<? super T> other) {
    return new CombinableMatcher<>(new AllOf<>(templatedListWith(other)));
  }

  public CombinableMatcher<T> or(Matcher<? super T> other) {
    return new CombinableMatcher<>(new AnyOf<>(templatedListWith(other)));
  }

  private ArrayList<Matcher<? super T>> templatedListWith(Matcher<? super T> other) {
    ArrayList<Matcher<? super T>> matchers = new ArrayList<>();
    matchers.add(matcher);
    matchers.add(other);
    return matchers;
  }

  /**
   * Creates a matcher that matches when both of the specified matchers match the examined object.
   * For example:
   * <pre>assertThat("fab", both(containsString("a")).and(containsString("b")))</pre>
   */
  public static <LHS> CombinableBothMatcher<LHS> both(Matcher<? super LHS> matcher) {
    return new CombinableBothMatcher<>(matcher);
  }
  
  public static final class CombinableBothMatcher<X> {
    private final Matcher<? super X> first;
    public CombinableBothMatcher(Matcher<? super X> matcher) {
        this.first = matcher;
    }
    public CombinableMatcher<X> and(Matcher<? super X> other) {
      return new CombinableMatcher<>(first).and(other);
    }
  }

  /**
   * Creates a matcher that matches when either of the specified matchers match the examined object.
   * For example:
   * <pre>assertThat("fan", either(containsString("a")).or(containsString("b")))</pre>
   */
  public static <LHS> CombinableEitherMatcher<LHS> either(Matcher<? super LHS> matcher) {
    return new CombinableEitherMatcher<>(matcher);
  }
  
  public static final class CombinableEitherMatcher<X> {
    private final Matcher<? super X> first;
    public CombinableEitherMatcher(Matcher<? super X> matcher) {
        this.first = matcher;
    }
    public CombinableMatcher<X> or(Matcher<? super X> other) {
      return new CombinableMatcher<>(first).or(other);
    }
  }
}
