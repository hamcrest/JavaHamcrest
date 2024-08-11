package org.hamcrest.core;

import org.hamcrest.Matcher;

/**
 * Tests if the argument is a string that starts with a specific substring.
 */
public class StringStartsWith extends SubstringMatcher {

    /**
     * Constructor, best used with {@link #startsWith(String)}.
     * @param prefix the expected start of the string.
     */
    public StringStartsWith(String prefix) { this(false, prefix); }

    /**
     * Constructor, best used with {@link #startsWith(String)} or
     * {@link #startsWithIgnoringCase(String)}.
     * @param ignoringCase whether to ignore case when matching
     * @param prefix the expected start of the string.
     */
    public StringStartsWith(boolean ignoringCase, String prefix) { super("starting with", ignoringCase, prefix); }

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
     * @return The matcher.
     */
    public static Matcher<String> startsWith(String prefix) { return new StringStartsWith(false, prefix); }

    /**
     * <p>
     * Creates a matcher that matches if the examined {@link String} starts with the specified
     * {@link String}, ignoring case
     * </p>
     * For example:
     * <pre>assertThat("myStringOfNote", startsWithIgnoringCase("My"))</pre>
     *
     * @param prefix
     *      the substring that the returned matcher will expect at the start of any examined string
     * @return The matcher.
     */
    public static Matcher<String> startsWithIgnoringCase(String prefix) { return new StringStartsWith(true, prefix); }

}
