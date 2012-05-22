/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Tests if the argument is a string that contains a substring.
 */
public class StringContains extends SubstringMatcher {
    public StringContains(String substring) {
        super(substring);
    }

    @Override
    protected boolean evalSubstringOf(String s) {
        return s.indexOf(substring) >= 0;
    }

    @Override
    protected String relationship() {
        return "containing";
    }

    /**
     * Creates a matcher that matches if the examined {@link String} contains the specified
     * {@link String} anywhere.
     * <p/>
     * For example:
     * <pre>assertThat("myStringOfNote", containsString("ring"))</pre>
     * 
     * @param substring
     *     the substring that the returned matcher will expect to find within any examined string
     * 
     */
    @Factory
    public static Matcher<String> containsString(String substring) {
        return new StringContains(substring);
    }

}