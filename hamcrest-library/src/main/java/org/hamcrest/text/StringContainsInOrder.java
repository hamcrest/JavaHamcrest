package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;

public class StringContainsInOrder extends TypeSafeMatcher<String> {
    private final Iterable<String> substrings;

    public StringContainsInOrder(Iterable<String> substrings) {
        this.substrings = substrings;
    }

    @Override
    public boolean matchesSafely(String s) {
        int fromIndex = 0;
        
        for (String substring : substrings) {
            fromIndex = s.indexOf(substring, fromIndex);
            if (fromIndex == -1) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("a string containing ")
                   .appendValueList("", ", ", "", substrings)
                   .appendText(" in order");
    }
    
    /**
     * Creates a matcher of {@link String} that matches when the examined string contains all of
     * the specified substrings, regardless of the order of their appearance.
     * <p/>
     * For example:
     * <pre>assertThat("myfoobarbaz", stringContainsInOrder(Arrays.asList("bar", "foo")))</pre>
     * 
     * @param substrings
     *     the substrings that must be contained within matching strings
     */
    @Factory
    public static Matcher<String> stringContainsInOrder(Iterable<String> substrings) {
        return new StringContainsInOrder(substrings);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string contains all of
     * the specified substrings, regardless of the order of their appearance.
     * <p/>
     * For example:
     * <pre>assertThat("myfoobarbaz", stringContainsInOrder("bar", "foo"))</pre>
     *
     * @param substrings
     *     the substrings that must be contained within matching strings
     */
    @Factory
    public static Matcher<String> stringContainsInOrder(String... substrings) {
        return new StringContainsInOrder(Arrays.asList(substrings));
    }
}
