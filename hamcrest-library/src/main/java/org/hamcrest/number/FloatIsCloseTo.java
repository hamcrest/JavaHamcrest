package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static java.lang.Math.abs;


/**
 * Is the value a number equal to a value within some range of
 * acceptable error?
 */
public class FloatIsCloseTo extends TypeSafeMatcher<Float> {
    private final float delta;
    private final float value;

    public FloatIsCloseTo(float value, float error) {
        this.delta = error;
        this.value = value;
    }

    @Override
    public boolean matchesSafely(Float item) {
        return actualDelta(item) <= delta;
    }

    @Override
    public void describeMismatchSafely(Float item, Description mismatchDescription) {
      mismatchDescription.appendValue(item)
                         .appendText(" differed by ")
                         .appendValue(actualDelta(item))
                         .appendText(", but delta is ")
                         .appendValue(delta);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a numeric value within ")
                .appendValue(delta)
                .appendText(" of ")
                .appendValue(value);
    }

    private float actualDelta(Float item) {
      return abs(item - value);
    }

    /**
     * Creates a matcher of {@link Float}s that matches when an examined float is equal
     * to the specified <code>operand</code>, within a range of +/- <code>error</code>.
     * For example:
     * <pre>assertThat(1.03f, is(closeTo(1.0f, 0.03f)))</pre>
     *
     * @param operand
     *     the expected value of matching floats
     * @param error
     *     the delta (+/-) within which matches will be allowed
     */
    public static Matcher<Float> closeTo(float operand, float error) {
        return new FloatIsCloseTo(operand, error);
    }
}
