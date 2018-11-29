
package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.regex.Pattern;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Matches blank Strings (and null).
 */
public final class IsBlankString extends TypeSafeMatcher<String> {
    private static final IsBlankString BLANK_INSTANCE = new IsBlankString();
    @SuppressWarnings("unchecked")
    private static final Matcher<String> NULL_OR_BLANK_INSTANCE = anyOf(nullValue(), BLANK_INSTANCE);

    private static final Pattern REGEX_WHITESPACE = Pattern.compile("\\s*");

    private IsBlankString() { }

    @Override
    public boolean matchesSafely(String item) {
        return REGEX_WHITESPACE.matcher(item).matches();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a blank string");
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string contains
     * zero or more whitespace characters and nothing else.
     * For example:
     * <pre>assertThat("  ", is(blankString()))</pre>
     */
    public static Matcher<String> blankString() {
        return BLANK_INSTANCE;
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * contains zero or more whitespace characters and nothing else.
     * For example:
     * <pre>assertThat(((String)null), is(blankOrNullString()))</pre>
     * 
     */
    public static Matcher<String> blankOrNullString() {
        return NULL_OR_BLANK_INSTANCE;
    }
}
