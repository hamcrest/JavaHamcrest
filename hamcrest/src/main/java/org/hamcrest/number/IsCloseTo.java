package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static java.lang.Math.abs;

/**
 * Is the value a number equal to a value within some range of acceptable error?
 */
public class IsCloseTo extends TypeSafeMatcher<Number> {

    private final double delta;
    private final double value;

    /**
     * Constructor, best called from {@link #closeTo(double, double)}.
     * @param value the expected value
     * @param error the acceptable difference from the expected value
     */
    public IsCloseTo(double value, double error) {
        this.delta = error;
        this.value = value;
    }

    @Override
    public boolean matchesSafely(Number item) {
        return actualDelta(item) <= 0.0;
    }

    @Override
    public void describeMismatchSafely(Number item, Description mismatchDescription) {
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

    private double actualDelta(Number item) {
      return abs(item.doubleValue() - value) - delta;
    }

    /**
     * Creates a matcher of {@link Number}s that matches when an examined number is equal
     * to the specified <code>operand</code>, within a range of +/- <code>error</code>.
     * Comparison is performed using {@link Number#doubleValue()}.
     * For example:
     * <pre>assertThat(1.03, is(closeTo(1.0, 0.03)))</pre>
     * <pre>assertThat(1.0f, is(closeTo(0.99, 0.1)))</pre>
     *
     * @param operand
     *     the expected value of matching numbers
     * @param error
     *     the delta (+/-) within which matches will be allowed
     * @return The matcher.
     */
    public static Matcher<Number> closeTo(double operand, double error) {
        return new IsCloseTo(operand, error);
    }

}
