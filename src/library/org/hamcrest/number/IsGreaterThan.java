/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.Formatting;
import org.hamcrest.Matcher;


/**
 * Is the value greater than another {@link java.lang.Comparable} value?
 */
public class IsGreaterThan implements Matcher {
    private final Comparable lowerLimit;

    public IsGreaterThan(Comparable lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public boolean match(Object arg) {
        return lowerLimit.compareTo(arg) < 0;
    }

    public void describeTo(StringBuffer buffer) {
        buffer.append("a value greater than ")
                .append(Formatting.toReadableString(lowerLimit));
    }
}
