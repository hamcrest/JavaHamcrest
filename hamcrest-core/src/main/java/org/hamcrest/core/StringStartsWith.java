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

    @Factory
    public static Matcher<String> startsWith(String substring) {
        return new StringStartsWith(substring);
    }

}