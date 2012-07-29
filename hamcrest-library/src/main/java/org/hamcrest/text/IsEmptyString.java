
package org.hamcrest.text;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsNull.nullValue;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Matches empty Strings (and null).
 */
public final class IsEmptyString extends BaseMatcher<String> {
    private static final IsEmptyString INSTANCE = new IsEmptyString();
    @SuppressWarnings("unchecked")
    private static final Matcher<String> NULL_OR_EMPTY_INSTANCE = anyOf(nullValue(), INSTANCE);

    @Override
    public boolean matches(Object item) {
        return item != null && item instanceof String && ((String) item).equals("");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an empty string");
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
        return INSTANCE;
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
