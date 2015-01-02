package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


/**
 * Is the value a number actually not a number (NaN)?
 */
public final class IsNaN extends TypeSafeMatcher<Double> {

    private IsNaN() { }

    @Override
    public boolean matchesSafely(Double item) {
        return Double.isNaN(item);
    }

    @Override
    public void describeMismatchSafely(Double item, Description mismatchDescription) {
        mismatchDescription.appendText("was ").appendValue(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a double value of NaN");
    }

    /**
     * Creates a matcher of {@link Double}s that matches when an examined double is not a number.
     * For example:
     * <pre>assertThat(Double.NaN, is(notANumber()))</pre>
     */
    public static Matcher<Double> notANumber() {
        return new IsNaN();
    }
}
