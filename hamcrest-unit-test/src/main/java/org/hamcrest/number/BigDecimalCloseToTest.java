package org.hamcrest.number;

import static org.hamcrest.number.BigDecimalCloseTo.closeTo;

import java.math.BigDecimal;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class BigDecimalCloseToTest  extends AbstractMatcherTest {

  @Override
  protected Matcher<?> createMatcher() {
    BigDecimal irrelevant = new BigDecimal("0.01");
    return closeTo(irrelevant, irrelevant);
  }
  
  public void testEvaluatesToTrueIfArgumentIsEqualToABigDecimalWithinSomeError() {
    Matcher<BigDecimal> p = closeTo(new BigDecimal("1.0"), new BigDecimal("0.5"));

    assertTrue(p.matches(new BigDecimal("1.0")));
    assertTrue(p.matches(new BigDecimal("0.5")));
    assertTrue(p.matches(new BigDecimal("1.5")));

    assertDoesNotMatch("too large", p, new BigDecimal("2.0"));
    assertMismatchDescription("<2.0> differed by <0.5>", p, new BigDecimal("2.0"));
    assertDoesNotMatch("number too small", p, new BigDecimal("0.0"));
    assertMismatchDescription("<0.0> differed by <0.5>", p, new BigDecimal("0.0"));
  }
  
  public void testEvaluatesToTrueIfArgumentHasDifferentScale() {
    Matcher<BigDecimal> p = closeTo(new BigDecimal("1.0"), new BigDecimal("0.5"));

    assertTrue(p.matches(new BigDecimal("1.000000")));
    assertTrue(p.matches(new BigDecimal("0.500000")));
    assertTrue(p.matches(new BigDecimal("1.500000")));

    assertDoesNotMatch("too large", p, new BigDecimal("2.000000"));
    assertMismatchDescription("<2.000000> differed by <0.5>", p, new BigDecimal("2.000000"));
    assertDoesNotMatch("number too small", p, new BigDecimal("0.000000"));
    assertMismatchDescription("<0.000000> differed by <0.5>", p, new BigDecimal("0.000000"));
  }

}
