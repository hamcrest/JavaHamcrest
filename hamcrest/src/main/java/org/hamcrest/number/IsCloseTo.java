package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Is the value a number equal to a value within some range of acceptable error?
 */
public class IsCloseTo extends TypeSafeMatcher<Number> {
    private final double error;
    private final double expected;

    public IsCloseTo(double expected, double error) {
        this.error = Math.abs(error);
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(Number actual) {
        return calcError(actual) <= error;
    }

    @Override
    public void describeMismatchSafely(Number item, Description mismatchDescription) {
      mismatchDescription.appendValue(item)
                         .appendText(" differed by ")
                         .appendValue(calcError(item));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a numeric value within ")
                .appendValue(error)
                .appendText(" of ")
                .appendValue(expected);
    }

    private double calcError(Number actual) {
      return Math.abs(expected - actual.doubleValue());
    }

    /**
     * Creates a matcher of {@link Number}s that matches when an examined number is equal
     * to the specified <code>operand</code>, within a range of +/- <code>error</code>.
     * For example:
     * <pre>assertThat(1.03, is(closeTo(1.0, 0.03)))</pre>
     * 
     * @param expected
     *     the expected value of matching numbers
     * @param error
     *     the absolute error (+/-) within which matches will be allowed
     */
    public static Matcher<Number> closeTo(double expected, double error) {
        return new IsCloseTo(expected, error);
    }
}
