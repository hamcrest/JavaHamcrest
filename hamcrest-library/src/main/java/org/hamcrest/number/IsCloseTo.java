/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;


/**
 * Is the value a number equal to a value within some range of
 * acceptable error?
 */
public class IsCloseTo extends TypeSafeMatcher<Double> {
    private final double delta;
    private final double value;

    public IsCloseTo(double value, double error) {
        this.delta = error;
        this.value = value;
    }

    @Override
    public boolean matchesSafely(Double item) {
        return actualDelta(item) <= 0.0;
    }

    @Override
    public void describeMismatchSafely(Double item, Description mismatchDescription) {
      mismatchDescription.appendValue(item)
                         .appendText(" differed by ")
                         .appendValue(actualDelta(item));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a numeric value within ")
                .appendValue(delta)
                .appendText(" of ")
                .appendValue(value);
    }

    private double actualDelta(Double item) {
      return (Math.abs((item - value)) - delta);
    }

    /**
     * Creates a matcher of {@link Double}s that matches when an examined double is equal
     * to the specified <code>operand</code>, within a range of +/- <code>error</code>.
     * <p/>
     * For example:
     * <pre>assertThat(1.03, is(closeTo(1.0, 0.03)))</pre>
     * 
     * @param operand
     *     the expected value of matching doubles
     * @param error
     *     the delta (+/-) within which matches will be allowed
     */
    @Factory
    public static Matcher<Double> closeTo(double operand, double error) {
        return new IsCloseTo(operand, error);
    }
}
