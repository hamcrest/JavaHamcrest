package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Factory;
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
      return effectiveDelta(item).compareTo(BigDecimal.ZERO) <= 0;
  }

  @Override
  public void describeMismatchSafely(BigDecimal item, Description mismatchDescription) {
      mismatchDescription.appendText("differed from ")
              .appendValue(value)
              .appendText(" by ")
              .appendValue(actualDelta(item));
  }

  @Override
  public void describeTo(Description description) {
      description.appendText("within ")
              .appendValue(delta)
              .appendText(" of ")
              .appendValue(value);
  }

  private BigDecimal actualDelta(BigDecimal item) {
      return item.subtract(value, MathContext.DECIMAL128).abs().stripTrailingZeros();
  }
    
  private BigDecimal effectiveDelta(BigDecimal item) {
      return actualDelta(item).subtract(delta, MathContext.DECIMAL128).stripTrailingZeros();
  }


    /**
   * Creates a matcher of {@link java.math.BigDecimal}s that matches when an examined BigDecimal is equal
   * to the specified <code>operand</code>, within a range of +/- <code>error</code>. The comparison for equality
   * is done by BigDecimals {@link java.math.BigDecimal#compareTo(java.math.BigDecimal)} method.
   * <p/>
   * For example:
   * <pre>assertThat(new BigDecimal("1.03"), is(closeTo(new BigDecimal("1.0"), new BigDecimal("0.03"))))</pre>
   * 
   * @param operand
   *     the expected value of matching BigDecimals
   * @param error
   *     the delta (+/-) within which matches will be allowed
   */
  @Factory
  public static Matcher<BigDecimal> closeTo(BigDecimal operand, BigDecimal error) {
      return new BigDecimalCloseTo(operand, error);
  }

}

