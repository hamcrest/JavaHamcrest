package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;

public class HasToString<T> implements Matcher<T> {

    private final Matcher<String> toStringMatcher;

    public HasToString(Matcher<String> toStringMatcher) {
        this.toStringMatcher = toStringMatcher;
    }

    public boolean matches(Object item) {
        return toStringMatcher.matches(item.toString());
    }

    public void describeTo(Description description) {
        description.appendText("asString(");
        toStringMatcher.describeTo(description);
        description.appendText(")");
    }
    
    @Factory
    public static <T> Matcher<T> asString(Matcher<String> toStringMatcher) {
        return new HasToString<T>(toStringMatcher);
    }
}
