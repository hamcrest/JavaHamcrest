package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static java.lang.Math.abs;

/**
 * Is the value a number equal to a value within some range of
 * acceptable error?
 */
public class DoubleCloseTo extends BaseCloseTo<Double> {

    public DoubleCloseTo(Double value, Double error) {
        super(value, error);
    }

    protected Double actualDelta(Double item) {
      return abs(item - value);
    }

    /**
     * Creates a matcher of {@link Double}s that matches when an examined double is equal
     * to the specified <code>operand</code>, within a range of +/- <code>error</code>.
     * For example:
     * <pre>assertThat(1.03, is(closeTo(1.0, 0.03)))</pre>
     *
     * @param operand
     *     the expected value of matching doubles
     * @param error
     *     the delta (+/-) within which matches will be allowed
     * @return The matcher.
     */
    public static Matcher<Double> closeTo(double operand, double error) {
        return new DoubleCloseTo(operand, error);
    }

}
