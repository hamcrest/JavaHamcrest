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
    private final double error;
    private final double value;

    public IsCloseTo(double value, double error) {
        this.error = error;
        this.value = value;
    }

    public boolean matchesSafely(Double item) {
        return Math.abs((item - value)) <= error;
    }

    public void describeTo(Description description) {
        description.appendText("a numeric value within ")
                .appendValue(error)
                .appendText(" of ")
                .appendValue(value);
    }

    @Factory
    public static Matcher<Double> closeTo(double operand, double error) {
        return new IsCloseTo(operand, error);
    }

}
