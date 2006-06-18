/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;

/**
 * Tests if a string is equal to another string, regardless of the case.
 */
public class IsEqualIgnoringCase implements Matcher<String> {

    private final String string;

    public IsEqualIgnoringCase(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
        }
        this.string = string;
    }

    public boolean match(String item) {
        if (item == null) {
            return false;
        } else {
            return string.equalsIgnoreCase(item);
        }
    }

    public void describeTo(Description description) {
        description.appendText("eqIgnoringCase(")
                .appendValue(string)
                .appendText(")");
    }

    @Factory
    public static Matcher<String> eqIgnoringCase(String string) {
        return new IsEqualIgnoringCase(string);
    }

}
