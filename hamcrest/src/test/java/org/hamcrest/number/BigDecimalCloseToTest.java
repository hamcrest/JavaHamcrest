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
