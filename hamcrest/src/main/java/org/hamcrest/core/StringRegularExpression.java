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

import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * @author borettim
 * @author sf105
 *
 */
public class StringRegularExpression extends TypeSafeDiagnosingMatcher<String> {

  protected StringRegularExpression(Pattern pattern) {
    this.pattern = pattern;
  }

  private Pattern pattern;

  @Override
  public void describeTo(Description description) {
    description.appendText("a string matching the pattern ").appendValue(pattern);
  }

  @Override
  protected boolean matchesSafely(String actual, Description mismatchDescription) {
    if (!pattern.matcher(actual).matches()) {
      mismatchDescription.appendText("the string was ").appendValue(actual);
      return false;
    }
    return true;
  }

  /**
   * Creates a matcher that checks if the examined string matches a specified {@link java.util.regex.Pattern}.
   *
   * <pre>
   * assertThat(&quot;abc&quot;, matchesRegex(Pattern.compile(&quot;&circ;[a-z]$&quot;));
   * </pre>
   *
   * @param pattern
   *            the pattern to be used.
   * @return The matcher.
   */
  public static Matcher<String> matchesRegex(Pattern pattern) {
    return new StringRegularExpression(pattern);
  }

  /**
   * Creates a matcher that checks if the examined string matches a specified regex.
   *
   * <pre>
   * assertThat(&quot;abc&quot;, matchesRegex(&quot;&circ;[a-z]+$&quot;));
   * </pre>
   *
   * @param regex
   *            The regex to be used for the validation.
   * @return The matcher.
   */
  public static Matcher<String> matchesRegex(String regex) {
    return matchesRegex(Pattern.compile(regex));
  }
}
