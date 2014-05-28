package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public abstract class SubstringMatcher extends TypeSafeMatcher<String> {

    // TODO: Replace String with CharSequence to allow for easy interoperability between
    //       String, StringBuffer, StringBuilder, CharBuffer, etc (joe).

    private final String relationship;
    private final boolean ignoringCase;
    protected final String substring;

    protected SubstringMatcher(String relationship, boolean ignoringCase, String substring) {
        this.relationship = relationship;
        this.ignoringCase = ignoringCase;
        this.substring = substring;
    }

    @Override
    public boolean matchesSafely(String item) {
        return evalSubstringOf(ignoringCase ? item.toLowerCase() :item);
    }
    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
      mismatchDescription.appendText("was \"").appendText(item).appendText("\"");
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("a string ")
                .appendText(relationship)
                .appendText(" ")
                .appendValue(substring);
        if (ignoringCase) {
            description.appendText(" ignoring case");
        }
    }

    protected String converted(String arg) { return ignoringCase ? arg.toLowerCase() : arg; }
    protected abstract boolean evalSubstringOf(String string);

}
