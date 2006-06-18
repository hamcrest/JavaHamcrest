package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public abstract class SubstringMatcher implements Matcher<String> {

    // TODO: Replace String with CharSequence to allow for easy interopability between
    //       String, StringBuffer, StringBuilder, CharBuffer, etc (joe).

    protected final String substring;

    protected SubstringMatcher(final String substring) {
        this.substring = substring;
    }

    public boolean match(String item) {
        return evalSubstringOf(item);
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