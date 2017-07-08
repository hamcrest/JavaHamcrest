package org.hamcrest.core;

import org.hamcrest.Matcher;

/**
 * Tests if the argument is a string that contains a substring.
 */
public class StringEndsWith extends SubstringMatcher {
    public StringEndsWith(boolean ignoringCase, CharSequence substring) { super("ending with", ignoringCase, substring); }

    @Override
    protected boolean evalSubstringOf(String s) {
        return converted(s).endsWith(converted(substring));
    }

    /**
     * Creates a matcher that matches if the examined {@link String} ends with the specified
     * {@link String}.
     * For example:
     * <pre>assertThat("myStringOfNote", endsWith("Note"))</pre>
     * 
     * @param suffix
     *      the substring that the returned matcher will expect at the end of any examined string
     */
    public static Matcher<CharSequence> endsWith(CharSequence suffix) {
        return new StringEndsWith(false, suffix);
    }

    /**
     * Creates a matcher that matches if the examined {@link String} ends with the specified
     * {@link String}, ignoring case.
     * For example:
     * <pre>assertThat("myStringOfNote", endsWithIgnoringCase("note"))</pre>
     *
     * @param suffix
     *      the substring that the returned matcher will expect at the end of any examined string
     */
    public static Matcher<CharSequence> endsWithIgnoringCase(CharSequence suffix) {
        return new StringEndsWith(true, suffix);
    }

}
