/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

/**
 * Tests if a string is equal to another string, regardless of the case.
 */
public class IsEqualIgnoringCase extends TypeSafeMatcher<String> {

    // TODO: Replace String with CharSequence to allow for easy interopability between
    //       String, StringBuffer, StringBuilder, CharBuffer, etc (joe).

    private final String string;

    public IsEqualIgnoringCase(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
        }
        this.string = string;
    }

    public boolean matchesSafely(String item) {
        return string.equalsIgnoreCase(item);
    }

    public void describeTo(Description description) {
        description.appendText("eqIgnoringCase(")
                .appendValue(string)
                .appendText(")");
    }

    @Factory
    public static Matcher<String> equalToIgnoringCase(String string) {
        return new IsEqualIgnoringCase(string);
    }

}
