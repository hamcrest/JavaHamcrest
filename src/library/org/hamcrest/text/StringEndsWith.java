/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Tests if the argument is a string that contains a substring.
 */
public class StringEndsWith extends SubstringMatcher {
    public StringEndsWith(String substring) {
        super(substring);
    }

    protected boolean evalSubstringOf(String s) {
        return s.endsWith(substring);
    }

    protected String relationship() {
        return "ending with";
    }

    @Factory
    public static Matcher<String> endsWith(String substring) {
        return new StringEndsWith(substring);
    }

}
