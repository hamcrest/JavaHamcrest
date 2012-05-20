/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import static java.lang.Character.isWhitespace;

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

    @Override
    public boolean matchesSafely(String item) {
        return stripSpace(string).equalsIgnoreCase(stripSpace(item));
    }
    
    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
      mismatchDescription.appendText("was  ").appendText(stripSpace(item));
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("equalToIgnoringWhiteSpace(")
                .appendValue(string)
                .appendText(")");
    }

    public String stripSpace(String toBeStripped) {
        final StringBuilder result = new StringBuilder();
        boolean lastWasSpace = true;
        for (int i = 0; i < toBeStripped.length(); i++) {
            char c = toBeStripped.charAt(i);
            if (isWhitespace(c)) {
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

    /**
     * Creates a matcher of {@link String} that matches when the examined string is equal to
     * the specified expectedString, when whitespace differences are (mostly) ignored.  To be
     * exact, the following whitespace rules are applied:
     * <ul>
     *   <li>all leading and trailing whitespace of both the expectedString and the examined string are ignored</li>
     *   <li>any remaining whitespace, appearing within either string, is collapsed to a single space before comparison</li>
     * </ul>
     * <p/>
     * For example:
     * <pre>assertThat("   my\tfoo  bar ", equalToIgnoringWhiteSpace(" my  foo bar"))</pre>
     * 
     * @param expectedString
     *     the expected value of matched strings
     */
    @Factory
    public static Matcher<String> equalToIgnoringWhiteSpace(String expectedString) {
        return new IsEqualIgnoringWhiteSpace(expectedString);
    }

}
