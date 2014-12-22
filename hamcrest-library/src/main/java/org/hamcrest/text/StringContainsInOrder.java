package org.hamcrest.text;

import org.hamcrest.Description;
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
    public void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText("was \"").appendText(item).appendText("\"");
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("a string containing ")
                   .appendValueList("", ", ", "", substrings)
                   .appendText(" in order");
    }
    
    /**
     * Creates a matcher of {@link String} that matches when the examined string contains all of
     * the specified substrings, considering the order of their appearance.
     * For example:
     * <pre>assertThat("myfoobarbaz", stringContainsInOrder(Arrays.asList("bar", "foo")))</pre>
     * fails as "foo" occurs before "bar" in the string "myfoobarbaz"
     * 
     * @param substrings
     *     the substrings that must be contained within matching strings
     */
    public static Matcher<String> stringContainsInOrder(Iterable<String> substrings) {
        return new StringContainsInOrder(substrings);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string contains all of
     * the specified substrings, considering the order of their appearance.
     * For example:
     * <pre>assertThat("myfoobarbaz", stringContainsInOrder("bar", "foo"))</pre>
     * fails as "foo" occurs before "bar" in the string "myfoobarbaz"
     *
     * @param substrings
     *     the substrings that must be contained within matching strings
     */
    public static Matcher<String> stringContainsInOrder(String... substrings) {
        return new StringContainsInOrder(Arrays.asList(substrings));
    }
}
