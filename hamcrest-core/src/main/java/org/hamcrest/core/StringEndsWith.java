/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Tests if the argument is a string that contains a substring.
 */
public class StringEndsWith extends SubstringMatcher {
    public StringEndsWith(String substring) {
        super(substring);
    }

    @Override
    protected boolean evalSubstringOf(String s) {
        return s.endsWith(substring);
    }

    @Override
    protected String relationship() {
        return "ending with";
    }

    /**
     * Creates a matcher that matches if the examined {@link String} ends with the specified
     * {@link String}.
     * <p/>
     * For example:
     * <pre>assertThat("myStringOfNote", endsWith("Note"))</pre>
     * 
     * @param suffix
     *      the substring that the returned matcher will expect at the end of any examined string
     */
    @Factory
    public static Matcher<String> endsWith(String suffix) {
        return new StringEndsWith(suffix);
    }

}
