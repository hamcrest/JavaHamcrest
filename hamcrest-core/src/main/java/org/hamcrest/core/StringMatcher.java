package org.hamcrest.core;

import org.hamcrest.BaseMatcher;

public abstract class StringMatcher extends BaseMatcher<String> {
    public boolean matches(Object item) {
        return item instanceof String && matchesSafely((String)item);
    }
    
    protected abstract boolean matchesSafely(String string);
    
    protected boolean isWhitespace(char ch) {
        return Character.isWhitespace(ch);
    }
}
