package org.hamcrest.number;

import org.hamcrest.Matcher;

import static java.lang.Math.abs;

/**
 * Is the value a number equal to a value within some range of
 * acceptable error?
 */
public class FloatCloseTo extends BaseCloseTo<Float> {

    public FloatCloseTo(Float value, Float error) {
        super(value, error);
    }

    protected Float actualDelta(Float item) {
      return abs(item - value);
    }

    /**
     * Creates a matcher of {@link Float}s that matches when an examined float is equal
     * to the specified <code>operand</code>, within a range of +/- <code>error</code>.
     * For example:
     * <pre>assertThat(1.03, is(closeTo(1.0, 0.03)))</pre>
     *
     * @param operand
     *     the expected value of matching floats
     * @param error
     *     the delta (+/-) within which matches will be allowed
     * @return The matcher.
     */
    public static Matcher<Float> closeTo(float operand, float error) {
        return new FloatCloseTo(operand, error);
    }

}
