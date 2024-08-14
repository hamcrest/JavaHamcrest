package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Common behaviour for matchers that check substrings.
 *
 * @see StringContains
 * @see StringEndsWith
 * @see StringStartsWith
 */
public abstract class SubstringMatcher extends TypeSafeMatcher<String> {

    // TODO: Replace String with CharSequence to allow for easy interoperability between
    //       String, StringBuffer, StringBuilder, CharBuffer, etc (joe).

    private final String relationship;
    private final boolean ignoringCase;
    /** The substring to match */
    protected final String substring;

    /**
     * Build a <code>SubstringMatcher</code>.
     * @param relationship a description of the matcher, such as "containing", "ending with", or "starting with"
     * @param ignoringCase true for case-insensitive match
     * @param substring the substring to match
     */
    protected SubstringMatcher(String relationship, boolean ignoringCase, String substring) {
        this.relationship = relationship;
        this.ignoringCase = ignoringCase;
        this.substring = substring;
        if (null == substring) {
            throw new IllegalArgumentException("missing substring");
        }
    }

    @Override
    public boolean matchesSafely(String item) {
        return evalSubstringOf(ignoringCase ? item.toLowerCase() :item);
    }
    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
      mismatchDescription.appendText("was \"").appendText(item).appendText("\"");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a string ")
                .appendText(relationship)
                .appendText(" ")
                .appendValue(substring);
        if (ignoringCase) {
            description.appendText(" ignoring case");
        }
    }

    /**
     * Helper method to allow subclasses to handle case insensitivity.
     * @param arg the string to adjust for case
     * @return the input string in lowercase if ignoring case, otherwise the original string
     */
    protected String converted(String arg) { return ignoringCase ? arg.toLowerCase() : arg; }

    /**
     * Checks if the input matches the specific substring.
     * @param string the string to check
     * @return the result of the match
     */
    protected abstract boolean evalSubstringOf(String string);

}
