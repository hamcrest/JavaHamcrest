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
package org.hamcrest;

import org.hamcrest.internal.ReflectiveTypeFinder;

/**
 * Supporting class for matching a feature of an object. Implement <code>featureValueOf()</code>
 * in a subclass to pull out the feature to be matched against. 
 *
 * @param <T> The type of the object to be matched
 * @param <U> The type of the feature to be matched
 */
public abstract class FeatureMatcher<T, U> extends TypeSafeDiagnosingMatcher<T> {
  private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("featureValueOf", 1, 0); 
  private final Matcher<? super U> subMatcher;
  private final String featureDescription;
  private final String featureName;
  
  /**
   * Constructor
   * @param subMatcher The matcher to apply to the feature
   * @param featureDescription Descriptive text to use in describeTo
   * @param featureName Identifying text for mismatch message
   */
  public FeatureMatcher(Matcher<? super U> subMatcher, String featureDescription, String featureName) {
    super(TYPE_FINDER);
    this.subMatcher = subMatcher;
    this.featureDescription = featureDescription;
    this.featureName = featureName;
  }
  
  /**
   * Implement this to extract the interesting feature.
   * @param actual the target object
   * @return the feature to be matched
   */
  protected abstract U featureValueOf(T actual);

  @Override
  protected boolean matchesSafely(T actual, Description mismatch) {
    final U featureValue = featureValueOf(actual);
    if (!subMatcher.matches(featureValue)) {
      mismatch.appendText(featureName).appendText(" ");
      subMatcher.describeMismatch(featureValue, mismatch);
      return false;
    }
    return true;
  }
      
  @Override
  public final void describeTo(Description description) {
    description.appendText(featureDescription).appendText(" ")
               .appendDescriptionOf(subMatcher);
  }
}
