/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.Matcher;


/**
 * Is the value a number equal to a value within some range of
 * acceptable error?
 */
public class IsCloseTo implements Matcher {
    private final double error;
    private final double value;

    public IsCloseTo(double value, double error) {
        this.error = error;
        this.value = value;
    }

    public boolean match(Object arg) {
        double argValue = ((Number) arg).doubleValue();
        return Math.abs((argValue - value)) <= error;
    }

    public void describeTo(StringBuffer buffer) {
        buffer.append("a numeric value within ")
                .append(error)
                .append(" of ")
                .append(value);
    }
}
