package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public abstract class SubstringMatcher implements Matcher {
    protected final String substring;

    protected SubstringMatcher(final String substring) {
        this.substring = substring;
    }

    public boolean match(Object o) {
        return o instanceof String && evalSubstringOf((String) o);
    }

    public void describeTo(Description description) {
        description.appendText("a string ")
                .appendText(relationship())
                .appendText(" ")
                .appendValue(substring);
    }

    protected abstract boolean evalSubstringOf(String string);

    protected abstract String relationship();
}