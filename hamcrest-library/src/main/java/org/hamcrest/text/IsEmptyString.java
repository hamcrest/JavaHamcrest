
package org.hamcrest.text;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsNull.nullValue;

import java.util.regex.Pattern;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Provides matchers that match null, empty, or blank Strings, or combinations thereof.
 */
public final class IsEmptyString extends BaseMatcher<String> {
    private static final IsEmptyString EMPTY_INSTANCE = new IsEmptyString(false);
    private static final IsEmptyString BLANK_INSTANCE = new IsEmptyString(true);
    @SuppressWarnings("unchecked")
    private static final Matcher<String> NULL_OR_EMPTY_INSTANCE = anyOf(nullValue(), EMPTY_INSTANCE);

    private static final Pattern REGEX_WHITESPACE = Pattern.compile("\\s*");

    private final boolean allowWhitespace;

    private IsEmptyString(boolean allowWhitespace) {
        this.allowWhitespace = allowWhitespace;
    }

    @Override
    public boolean matches(Object item) {
        if (item == null || !(item instanceof String)) {
            return false;
        }
        final String candidate = (String)item;
        return allowWhitespace ? REGEX_WHITESPACE.matcher(candidate).matches() : candidate.equals("");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(allowWhitespace ? "a blank" : "an empty").appendText(" string");
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string has zero length.
     * <p/>
     * For example:
     * <pre>assertThat("", isEmptyString())</pre>
     * 
     * @deprecated use is(emptyString()) instead
     */
    @Deprecated
    @Factory
    public static Matcher<String> isEmptyString() {
        return emptyString();
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string has zero length.
     * <p/>
     * For example:
     * <pre>assertThat("", is(emptyString()))</pre>
     * 
     */
    @Factory
    public static Matcher<String> emptyString() {
        return EMPTY_INSTANCE;
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string contains
     * zero or more whitespace characters and nothing else.
     * <p/>
     * For example:
     * <pre>assertThat("  ", is(emptyString()))</pre>
     */
    @Factory
    public static Matcher<String> blankString() {
        return BLANK_INSTANCE;
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * has zero length.
     * <p/>
     * For example:
     * <pre>assertThat(((String)null), isEmptyOrNullString())</pre>
     * 
     * @deprecated use is(emptyOrNullString()) instead
     * 
     */
    @Deprecated
    @Factory
    public static Matcher<String> isEmptyOrNullString() {
        return emptyOrNullString();
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * has zero length.
     * <p/>
     * For example:
     * <pre>assertThat(((String)null), is(emptyOrNullString()))</pre>
     * 
     */
    @Factory
    public static Matcher<String> emptyOrNullString() {
        return NULL_OR_EMPTY_INSTANCE;
    }
}
