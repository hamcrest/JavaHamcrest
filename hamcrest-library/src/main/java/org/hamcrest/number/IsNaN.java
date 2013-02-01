/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


/**
 * Is the value a number actually not a number (NaN)?
 */
public class IsNaN extends TypeSafeMatcher<Double> {

    private IsNaN() { }

    @Override
    public boolean matchesSafely(Double item) {
        return Double.isNaN(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("NaN");
    }

    /**
     * Creates a matcher of {@link Double}s that matches when an examined double is not a number.
     * <p/>
     * For example:
     * <pre>assertThat(Double.NaN, is(notANumber()))</pre>
     */
    @Factory
    public static Matcher<Double> notANumber() {
        return new IsNaN();
    }
}
