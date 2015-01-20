package org.hamcrest.core;

import org.hamcrest.Matcher;

/**
 * Tests if the argument is a string that contains a substring.
 */
public class StringStartsWith extends SubstringMatcher {
    public StringStartsWith(boolean ignoringCase, String substring) { super("starting with", ignoringCase, substring); }

    @Override
    protected boolean evalSubstringOf(String s) { return converted(s).startsWith(converted(substring)); }

    /**
     * <p>
     * Creates a matcher that matches if the examined {@link String} starts with the specified
     * {@link String}.
     * </p>
     * For example:
     * <pre>assertThat("myStringOfNote", startsWith("my"))</pre>
     * 
     * @param prefix
     *      the substring that the returned matcher will expect at the start of any examined string
     */
    public static Matcher<String> startsWith(String prefix) { return new StringStartsWith(false, prefix); }

    /**
     * <p>
     * Creates a matcher that matches if the examined {@link String} starts with the specified
     * {@link String}, ignoring case
     * </p>
     * For example:
     * <pre>assertThat("myStringOfNote", startsWith("my"))</pre>
     *
     * @param prefix
     *      the substring that the returned matcher will expect at the start of any examined string
     */
    public static Matcher<String> startsWithIgnoringCase(String prefix) { return new StringStartsWith(true, prefix); }

}
