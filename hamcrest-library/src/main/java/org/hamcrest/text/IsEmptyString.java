
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

    public boolean matches(Object item) {
        return item != null && item instanceof String && ((String) item).equals("");
    }

    public void describeTo(Description description) {
        description.appendText("an empty string");
    }

    /**
     * Matches if value is null or zero-length string.
     */
    @Factory
    public static Matcher<String> isEmptyString() {
        return INSTANCE;
    }

    /**
     * Matches if value is null or zero-length string.
     */
    @Factory
    public static Matcher<String> isEmptyOrNullString() {
        return NULL_OR_EMPTY_INSTANCE;
    }
}
