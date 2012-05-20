package org.hamcrest.object;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.core.IsEqual.equalTo;

public class HasToString<T> extends FeatureMatcher<T, String> {
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
     * <p/>
     * For example:
     * <pre>assertThat(true, hasToString(equalTo("TRUE")))</pre>
     * 
     * @param toStringMatcher
     *     the matcher used to verify the toString result
     */
    @Factory
    public static <T> Matcher<T> hasToString(Matcher<? super String> toStringMatcher) {
        return new HasToString<T>(toStringMatcher);
    }

    /**
     * Creates a matcher that matches any examined object whose <code>toString</code> method
     * returns a value equalTo the specified string.
     * <p/>
     * For example:
     * <pre>assertThat(true, hasToString("TRUE"))</pre>
     * 
     * @param expectedToString
     *     the expected toString result
     */
    @Factory
    public static <T> Matcher<T> hasToString(String expectedToString) {
        return new HasToString<T>(equalTo(expectedToString));
    }
}
