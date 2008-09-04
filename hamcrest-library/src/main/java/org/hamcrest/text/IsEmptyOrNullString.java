
package org.hamcrest.text;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Matches empty Strings (and null).
 */
public final class IsEmptyOrNullString extends BaseMatcher<String> {
    private static final IsEmptyOrNullString INSTANCE = new IsEmptyOrNullString();

    public boolean matches(Object item) {
        return item == null || ((item instanceof String) && ((String) item).equals(""));
    }

    public void describeTo(Description description) {
        description.appendText("an empty string or null");
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
        return INSTANCE;
    }
}
