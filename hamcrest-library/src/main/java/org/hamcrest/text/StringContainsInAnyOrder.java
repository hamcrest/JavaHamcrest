package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;

public class StringContainsInAnyOrder extends TypeSafeMatcher<String> {
    private final Iterable<String> substrings;

    public StringContainsInAnyOrder(Iterable<String> substrings) {
        this.substrings = substrings;
    }

    @Override
    public boolean matchesSafely(String s) {
        for (String substring : substrings) {
            int index = s.indexOf(substring);
            if (index == -1) {
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
                .appendText(" in any order");
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string contains all of
     * the specified substrings, regardless of the order of their appearance.
     * <p/>
     * For example:
     * <pre>assertThat("myfoobarbaz", stringContainsInAnyOrder("bar", "foo"))</pre>
     *
     * @param substrings
     *     the substrings that must be contained within matching strings
     */
    public static Matcher<String> stringContainsInAnyOrder(Iterable<String> substrings) {
        return new StringContainsInAnyOrder(substrings);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string contains all of
     * the specified substrings, regardless of the order of their appearance.
     * <p/>
     * For example:
     * <pre>assertThat("myfoobarbaz", stringContainsInAnyOrder("bar", "foo"))</pre>
     *
     * @param substrings
     *     the substrings that must be contained within matching strings
     */
    public static Matcher<String> stringContainsInAnyOrder(String... substrings) {
        return new StringContainsInAnyOrder(Arrays.asList(substrings));
    }
}