package org.hamcrest.object;

import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class HasToString<T> extends BaseMatcher<T> {

    private final Matcher<String> toStringMatcher;

    public HasToString(Matcher<String> toStringMatcher) {
        this.toStringMatcher = toStringMatcher;
    }

    public boolean matches(Object item) {
        return item != null && toStringMatcher.matches(item.toString());
    }

    public void describeTo(Description description) {
        description
	        .appendText("asString(")
	        .appendDescriptionOf(toStringMatcher)
	        .appendText(")");
    }

    @Factory
    public static <T> Matcher<T> hasToString(Matcher<String> toStringMatcher) {
        return new HasToString<T>(toStringMatcher);
    }

    @Factory
    public static <T> Matcher<T> hasToString(String expectedToString) {
        return new HasToString<T>(equalTo(expectedToString));
    }
}
