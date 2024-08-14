package org.hamcrest.object;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * A Matcher that checks the output of the <code>toString()</code> method.
 * @param <T> The Matcher type.
 */
public class HasToString<T> extends FeatureMatcher<T, String> {

    /**
     * Constructor, best called from {@link #hasToString(String)} or {@link #hasToString(Matcher)}.
     * @param toStringMatcher expected result from calling <code>toString()</code>
     */
    public HasToString(Matcher<? super String> toStringMatcher) {
      super(toStringMatcher, "with toString()", "toString()");
    }

    @Override
    protected String featureValueOf(T actual) {
      return String.valueOf(actual);
    }

    /**
     * Creates a matcher that matches any examined object whose <code>toString</code> method
     * returns a value that satisfies the specified matcher.
     * For example:
     * <pre>assertThat(true, hasToString(equalTo("TRUE")))</pre>
     *
     * @param <T>
     *     the matcher type.
     * @param toStringMatcher
     *     the matcher used to verify the toString result
     * @return The matcher.
     */
    public static <T> Matcher<T> hasToString(Matcher<? super String> toStringMatcher) {
        return new HasToString<>(toStringMatcher);
    }

    /**
     * Creates a matcher that matches any examined object whose <code>toString</code> method
     * returns a value equalTo the specified string.
     * For example:
     * <pre>assertThat(true, hasToString("TRUE"))</pre>
     *
     * @param <T>
     *     the matcher type.
     * @param expectedToString
     *     the expected toString result
     * @return The matcher.
     */
    public static <T> Matcher<T> hasToString(String expectedToString) {
        return new HasToString<>(equalTo(expectedToString));
    }

}
