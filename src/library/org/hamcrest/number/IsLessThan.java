/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.Formatting;
import org.hamcrest.Matcher;
import org.hamcrest.Description;


/**
 * Is the value less than another {@link java.lang.Comparable} value?
 */
public class IsLessThan implements Matcher {
    private final Comparable upperLimit;

    public IsLessThan(Comparable upperLimit) {
        this.upperLimit = upperLimit;
    }

    public boolean match(Object arg) {
        return upperLimit.compareTo(arg) > 0;
    }

    public void describeTo(Description description) {
        description.append("a value less than ")
                .append(Formatting.toReadableString(upperLimit));
    }
}
