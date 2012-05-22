/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Tests if the argument is a string that contains a substring.
 */
public class StringStartsWith extends SubstringMatcher {
    public StringStartsWith(String substring) {
        super(substring);
    }

    @Override
    protected boolean evalSubstringOf(String s) {
        return s.startsWith(substring);
    }

    @Override
    protected String relationship() {
        return "starting with";
    }

    /**
     * Creates a matcher that matches if the examined {@link String} starts with the specified
     * {@link String}.
     * <p/>
     * For example:
     * <pre>assertThat("myStringOfNote", startsWith("my"))</pre>
     * 
     * @param prefix
     *      the substring that the returned matcher will expect at the start of any examined string
     */
    @Factory
    public static Matcher<String> startsWith(String prefix) {
        return new StringStartsWith(prefix);
    }

}