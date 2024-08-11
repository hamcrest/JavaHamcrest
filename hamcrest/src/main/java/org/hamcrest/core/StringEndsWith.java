package org.hamcrest.core;

import org.hamcrest.Matcher;

/**
 * Tests if the argument is a string that ends with a specific substring.
 */
public class StringEndsWith extends SubstringMatcher {

    /**
     * Constructor, best used with {@link #endsWith(String)}.
     * @param suffix the expected end of the string.
     */
    public StringEndsWith(String suffix) { this(false, suffix); }

    /**
     * Constructor, best used with {@link #endsWith(String)} or
     * {@link #endsWithIgnoringCase(String)}.
     * @param ignoringCase whether to ignore case when matching
     * @param suffix the expected end of the string.
     */
    public StringEndsWith(boolean ignoringCase, String suffix) { super("ending with", ignoringCase, suffix); }

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
     * @return The matcher.
     */
    public static Matcher<String> endsWith(String suffix) {
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
     * @return The matcher.
     */
    public static Matcher<String> endsWithIgnoringCase(String suffix) {
        return new StringEndsWith(true, suffix);
    }

}
