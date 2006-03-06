/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.Formatting;
import org.hamcrest.Matcher;

/**
 * Tests if a string is equal to another string, regardless of the case.
 */
public class IsEqualIgnoringCase implements Matcher {

    private final String string;

    public IsEqualIgnoringCase(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
        }
        this.string = string;
    }

    public boolean match(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof String)) {
            return false;
        } else {
            return string.equalsIgnoreCase((String) o);
        }
    }

    public void describeTo(StringBuffer buffer) {
        buffer.append("eqIgnoringCase(")
                .append(Formatting.toReadableString(string))
                .append(")");
    }
}
