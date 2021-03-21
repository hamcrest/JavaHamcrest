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
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * @author Steve Freeman 2016 http://www.hamcrest.com
 */
public class ArrayAsIterableMatcher<E> extends TypeSafeMatcher<E[]> {
  protected final TypeSafeDiagnosingMatcher<Iterable<? extends E>> iterableMatcher;
  private final String message;
  protected final Collection<Matcher<? super E>> matchers;

  public ArrayAsIterableMatcher(
        TypeSafeDiagnosingMatcher<Iterable<? extends E>> iterableMatcher,
        Collection<Matcher<? super E>> matchers,
        String message)
  {
    this.matchers = matchers;
    this.iterableMatcher = iterableMatcher;
    this.message = message;
  }

  @Override
  public boolean matchesSafely(E[] item) {
      return iterableMatcher.matches(asList(item));
  }

  @Override
  public void describeMismatchSafely(E[] item, Description mismatchDescription) {
    iterableMatcher.describeMismatch(asList(item), mismatchDescription);
  }

  @Override
  public void describeTo(Description description) {
      description.appendList("[", ", ", "]", matchers)
          .appendText(" ").appendText(message);
  }
}
