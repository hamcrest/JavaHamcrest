package org.hamcrest.number;

import org.hamcrest.Matcher;

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalCloseTo extends BaseCloseTo<BigDecimal> {

    public BigDecimalCloseTo(BigDecimal value, BigDecimal error) {
        super(value, error);
    }

    protected BigDecimal actualDelta(BigDecimal item) {
        return item.subtract(value, MathContext.DECIMAL128).abs();
    }

    /**
     * Creates a matcher of {@link java.math.BigDecimal}s that matches when an examined BigDecimal is equal
     * to the specified <code>operand</code>, within a range of +/- <code>error</code>. The comparison for equality
     * is done by BigDecimals {@link java.math.BigDecimal#compareTo(java.math.BigDecimal)} method.
     * For example:
     * <pre>assertThat(new BigDecimal("1.03"), is(closeTo(new BigDecimal("1.0"), new BigDecimal("0.03"))))</pre>
     *
     * @param operand the expected value of matching BigDecimals
     * @param error   the delta (+/-) within which matches will be allowed
     * @return The matcher.
     */
    public static Matcher<BigDecimal> closeTo(BigDecimal operand, BigDecimal error) {
        return new BigDecimalCloseTo(operand, error);
    }
}
