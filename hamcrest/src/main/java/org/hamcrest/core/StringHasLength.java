package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Tests if the argument is a string that has a specific length.
 */
public class StringHasLength extends TypeSafeMatcher<String> {

    private final int length;

    public StringHasLength(int length) {
        this.length = length;
    }

    /**
     * <p>
     * Creates a matcher that matches if the examined {@link String} has the specified length.
     * </p>
     * For example:
     * <pre>assertThat("myStringOfNote", hasLength(14))</pre>
     *
     * @param length
     *      the length the returned matcher will expect to be equal to the length of the examined string
     */
    public static Matcher<String> hasLength(int length) {
        return new StringHasLength(length);
    }

    @Override
    protected boolean matchesSafely(String item) {
        if (item == null) {
            return false;
        }

        return item.length() == this.length;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has length ").appendValue(length);
    }

    @Override
    protected void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText("was length ").appendValue(item.length());
    }
}