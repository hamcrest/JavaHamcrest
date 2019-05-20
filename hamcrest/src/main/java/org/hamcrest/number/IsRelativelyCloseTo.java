package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Is the value a number equal to a value within some range of acceptable error?
 */
public class IsRelativelyCloseTo extends TypeSafeMatcher<Number> {
    private final double relativeError;
    private final double expected;

    public IsRelativelyCloseTo(double expected, double relativeError) {
        if (expected == 0) {
            throw new IllegalArgumentException("relative error is undefined with an expected value of zero");
        }
    
        this.relativeError = Math.abs(relativeError);
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(Number actual) {
        return calcRelativeError(actual) <= relativeError;
    }

    @Override
    public void describeMismatchSafely(Number item, Description mismatchDescription) {
      mismatchDescription.appendValue(item)
                         .appendText(" differed by ")
                         .appendValue(calcAbsoluteError(item));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a numeric value within ")
                .appendValue(expected * relativeError)
                .appendText(" of ")
                .appendValue(expected);
    }

    private double calcRelativeError(Number actual) {
      return Math.abs(calcAbsoluteError(actual) / expected));
    }
    
    private double calcAbsoluteError(Number actual) {
      return Math.abs(expected - actual.doubleValue());
    }

    /**
     * Creates a matcher of {@link Number}s that matches when an examined number is equal
     * to the specified <code>operand</code>, within a range of +/- <code>relative error</code>.
     * The expected number must be measured on a
     * <a href="https://en.wikipedia.org/wiki/Level_of_measurement#Ratio_scale">ratio scale</a>.
     * For example:
     * <pre>assertThat(103.0, is(closeTo(100.0, 0.03)))</pre>
     * 
     * @param expected
     *     the expected value of matching numbers, which may not be zero
     * @param relativeError
     *     the relative error (+/-) within which matches will be allowed
     */
    public static Matcher<Number> relativelyCloseTo(double expected, double relativeError) {
        return new IsRelativelyCloseTo(expected, relativeError);
    }
}
