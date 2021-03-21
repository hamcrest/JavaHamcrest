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
package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalCloseTo extends TypeSafeMatcher<BigDecimal> {

  private final BigDecimal delta;
  private final BigDecimal value;

  public BigDecimalCloseTo(BigDecimal value, BigDecimal error) {
      this.delta = error;
      this.value = value;
  }

  @Override
  public boolean matchesSafely(BigDecimal item) {
      return actualDelta(item).compareTo(BigDecimal.ZERO) <= 0;
  }

  @Override
  public void describeMismatchSafely(BigDecimal item, Description mismatchDescription) {
      mismatchDescription.appendValue(item)
              .appendText(" differed by ")
              .appendValue(actualDelta(item))
              .appendText(" more than delta ")
              .appendValue(delta);
  }

  @Override
  public void describeTo(Description description) {
      description.appendText("a numeric value within ")
              .appendValue(delta)
              .appendText(" of ")
              .appendValue(value);
  }

  private BigDecimal actualDelta(BigDecimal item) {
      return item.subtract(value, MathContext.DECIMAL128).abs().subtract(delta, MathContext.DECIMAL128).stripTrailingZeros();
  }

  /**
   * Creates a matcher of {@link java.math.BigDecimal}s that matches when an examined BigDecimal is equal
   * to the specified <code>operand</code>, within a range of +/- <code>error</code>. The comparison for equality
   * is done by BigDecimals {@link java.math.BigDecimal#compareTo(java.math.BigDecimal)} method.
   * For example:
   * <pre>assertThat(new BigDecimal("1.03"), is(closeTo(new BigDecimal("1.0"), new BigDecimal("0.03"))))</pre>
   * 
   * @param operand
   *     the expected value of matching BigDecimals
   * @param error
   *     the delta (+/-) within which matches will be allowed
   */
  public static Matcher<BigDecimal> closeTo(BigDecimal operand, BigDecimal error) {
      return new BigDecimalCloseTo(operand, error);
  }

}

