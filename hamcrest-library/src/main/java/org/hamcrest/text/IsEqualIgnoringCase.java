package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Tests if a string is equal to another string, regardless of the case.
 */
public class IsEqualIgnoringCase extends TypeSafeMatcher<String> {

    // TODO: Replace String with CharSequence to allow for easy interoperability between
    //       String, StringBuffer, StringBuilder, CharBuffer, etc (joe).

    private final String string;

    public IsEqualIgnoringCase(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
        }
        this.string = string;
    }

    @Override
    public boolean matchesSafely(String item) {
        return string.equalsIgnoreCase(item);
    }

    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
      mismatchDescription.appendText("was ").appendValue(item);
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("equalToIgnoringCase(")
                .appendValue(string)
                .appendText(")");
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is equal to
     * the specified expectedString, ignoring case.
     * For example:
     * <pre>assertThat("Foo", equalToIgnoringCase("FOO"))</pre>
     * 
     * @param expectedString
     *     the expected value of matched strings
     */
    public static Matcher<String> equalToIgnoringCase(String expectedString) {
        return new IsEqualIgnoringCase(expectedString);
    }

}
