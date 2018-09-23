package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author Marco Leichsenring
 */
public class CharSequenceLength extends TypeSafeMatcher<CharSequence> {
    private final int length;

    public CharSequenceLength(int length) {
        this.length = length;
    }

    @Override
    public boolean matchesSafely(CharSequence chars) {
        return chars.length() == length;
    }

    @Override
    public void describeMismatchSafely(CharSequence chars, Description mismatchDescription) {
        mismatchDescription.appendValue(chars).appendText(" does not have length ").appendValue(length);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a string with length ").appendValue(length);
    }

    /**
     * Creates a matcher of {@link CharSequence} that matches when a char sequence has the length
     * of the specified <code>argument</code>.
     * For example:
     * 
     * <pre>
     * assertThat("text", length(4))
     * </pre>
     * 
     * @param length
     *            the expected length of the string
     */
    public static Matcher<CharSequence> length(int length) {
        return new CharSequenceLength(length);
    }
}
