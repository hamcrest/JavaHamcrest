package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.SubstringMatcher;

public class StringContainsIgnoringCase extends SubstringMatcher {

    public StringContainsIgnoringCase(String substring) {
        super(substring);
    }

    @Override
    public boolean matchesSafely(String test) {
        return evalSubstringOf(test);
    }

    @Override
    protected boolean evalSubstringOf(String s) {
        return s.toLowerCase().contains(substring.toLowerCase()); //substring is inherited from SubstringMatcher
    }

    @Override
    protected String relationship() {
        return "containing ignoring case";
    }

    @Factory
    public static <T> Matcher<String> containsStringIgnoringCase(String substring) {
        return new StringContainsIgnoringCase(substring);
    }
}
