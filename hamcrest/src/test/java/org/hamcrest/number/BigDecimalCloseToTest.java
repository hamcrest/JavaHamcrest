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

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.math.BigDecimal;

import static org.hamcrest.number.BigDecimalCloseTo.closeTo;

public class BigDecimalCloseToTest  extends AbstractMatcherTest {
  private final Matcher<BigDecimal> matcher = closeTo(new BigDecimal("1.0"), new BigDecimal("0.5"));

  @Override
  protected Matcher<?> createMatcher() {
    BigDecimal irrelevant = new BigDecimal("0.01");
    return closeTo(irrelevant, irrelevant);
  }
  
  public void testEvaluatesToTrueIfArgumentIsEqualToABigDecimalWithinSomeError() {
    assertTrue(matcher.matches(new BigDecimal("1.0")));
    assertTrue(matcher.matches(new BigDecimal("0.5")));
    assertTrue(matcher.matches(new BigDecimal("1.5")));

    assertDoesNotMatch("too large", matcher, new BigDecimal("2.0"));
    assertMismatchDescription("<2.0> differed by <0.5> more than delta <0.5>", matcher, new BigDecimal("2.0"));
    assertDoesNotMatch("number too small", matcher, new BigDecimal("0.0"));
    assertMismatchDescription("<0.0> differed by <0.5> more than delta <0.5>", matcher, new BigDecimal("0.0"));
  }
  
  public void testEvaluatesToTrueIfArgumentHasDifferentScale() {
    assertTrue(matcher.matches(new BigDecimal("1.000000")));
    assertTrue(matcher.matches(new BigDecimal("0.500000")));
    assertTrue(matcher.matches(new BigDecimal("1.500000")));

    assertDoesNotMatch("too large", matcher, new BigDecimal("2.000000"));
    assertMismatchDescription("<2.000000> differed by <0.5> more than delta <0.5>", matcher, new BigDecimal("2.000000"));
    assertDoesNotMatch("number too small", matcher, new BigDecimal("0.000000"));
    assertMismatchDescription("<0.000000> differed by <0.5> more than delta <0.5>", matcher, new BigDecimal("0.000000"));
  }

  public void test_is_self_describing() {
    assertDescription("a numeric value within <0.5> of <1.0>", matcher);
  }

}
