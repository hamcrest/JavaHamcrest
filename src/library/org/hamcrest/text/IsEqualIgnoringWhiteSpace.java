/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

/**
 * Tests if a string is equal to another string, ignoring any changes in whitespace.
 */
public class IsEqualIgnoringWhiteSpace extends TypeSafeMatcher<String> {

    // TODO: Replace String with CharSequence to allow for easy interopability between
    //       String, StringBuffer, StringBuilder, CharBuffer, etc (joe).

    private final String string;

    public IsEqualIgnoringWhiteSpace(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
        }
        this.string = string;
    }

    public boolean matchesSafely(String item) {
        return stripSpace(string).equalsIgnoreCase(stripSpace(item));
    }

    public void describeTo(Description description) {
        description.appendText("eqIgnoringWhiteSpace(")
                .appendValue(string)
                .appendText(")");
    }

    public String stripSpace(String string) {
        StringBuilder result = new StringBuilder();
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

    @Factory
    public static Matcher<String> equalToIgnoringWhiteSpace(String string) {
        return new IsEqualIgnoringWhiteSpace(string);
    }

}
