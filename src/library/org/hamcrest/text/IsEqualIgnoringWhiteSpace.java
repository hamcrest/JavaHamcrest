/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.Formatting;
import org.hamcrest.Matcher;

/**
 * Tests if a string is equal to another string, ignoring any changes in whitespace.
 */
public class IsEqualIgnoringWhiteSpace implements Matcher {

    private final String string;

    public IsEqualIgnoringWhiteSpace(String string) {
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
            return stripSpace(string).equalsIgnoreCase(stripSpace((String)o));
        }
    }

    public void describeTo(StringBuffer buffer) {
        buffer.append("eqIgnoringWhiteSpace(")
                .append(Formatting.toReadableString(string))
                .append(")");
    }

    public String stripSpace(String string) {
        StringBuffer result = new StringBuffer();
        boolean lastWasSpace = true;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (Character.isWhitespace(c)) {
                if (!lastWasSpace) {
                    result.append(' ');
                }
                lastWasSpace = true;
            } else {
                result.append(c);
                lastWasSpace = false;
            }
        }
        return result.toString().trim();
    }

}
