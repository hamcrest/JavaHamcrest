/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

/**
 * Tests if the argument is a string that contains a substring.
 */
public class StringContains extends SubstringMatcher {
    public StringContains(String substring) {
        super(substring);
    }

    protected boolean evalSubstringOf(String s) {
        return s.indexOf(substring) >= 0;
    }

    protected String relationship() {
        return "containing";
    }
}