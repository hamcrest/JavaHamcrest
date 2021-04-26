package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Tests if a string is equal to another string, compressing any changes in whitespace.
 */

public class IsEqualCompressingWhiteSpace extends TypeSafeMatcher<String> {

    // TODO: Replace String with CharSequence to allow for easy interoperability between
    //       String, StringBuffer, StringBuilder, CharBuffer, etc (joe).

    /**
     * enum the whitespace type where SPACE is '\s', TAB is '\t', LINE_FEED is '\n',
     * FORM_FEED is '\f', CARRIAGE_RETURN is '\r', MIX is combined type above.
     * @author Koko
     */
    enum whiteSpaceType
    {
        SPACE,
        TAB,
        LINE_FEED,
        FORM_FEED,
        CARRIAGE_RETURN,
        MIX
    }

    private final String string;
    private final whiteSpaceType type;

    private IsEqualCompressingWhiteSpace(String string, whiteSpaceType type) {
        if (string == null) {
            throw new IllegalArgumentException("Non-null value required");
        }
        this.string = string;
        this.type = type;
    }

    @Override
    public boolean matchesSafely(String item) {
        return stripSpaces(string).equals(stripSpaces(item));
    }
    
    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
      mismatchDescription.appendText("was ").appendValue(item);
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("a string equal to ")
                .appendValue(string)
                .appendText(" compressing white space");
    }

    /**
     * strip space on the head and tail of the string;
     * besides that replace all whitespace specified by this.type with " "
     * strip redundant space between words
     * @author Koko
     * @param toBeStripped
     *     string to be stripped
     */
    private String stripSpaces(String toBeStripped) {
        if (this.type == whiteSpaceType.TAB){
            return toBeStripped.replaceAll("[\\p{Z}\\t]+", " ").trim();
        }
        else if (this.type == whiteSpaceType.LINE_FEED){
            return toBeStripped.replaceAll("[\\p{Z}\\n]+", " ").trim();
        }
        else if (this.type == whiteSpaceType.FORM_FEED){
            return toBeStripped.replaceAll("[\\p{Z}\\f]+", " ").trim();
        }
        else if (this.type == whiteSpaceType.CARRIAGE_RETURN){
            return toBeStripped.replaceAll("[\\p{Z}\\r]+", " ").trim();
        }
        else if (this.type == whiteSpaceType.SPACE){
            return toBeStripped.replaceAll("[\\p{Z}]+", " ").trim();
        }
        else{
            return toBeStripped.replaceAll("[\\p{Z}\\t\\n\\f\\r]+", " ").trim();
        }
    }

    /**
     * @deprecated {@link #equalToCompressingWhiteSpace(String)}
     * @param expectedString
     *     the expected value of matched strings
     */
    public static Matcher<String> equalToIgnoringWhiteSpace(String expectedString, whiteSpaceType type) {
        return new IsEqualCompressingWhiteSpace(expectedString, type);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is equal to
     * the specified expectedString, when whitespace differences are (mostly) ignored.  To be
     * exact, the following whitespace rules are applied:
     * <ul>
     *   <li>all leading and trailing whitespace of both the expectedString and the examined string are ignored</li>
     *   <li>any remaining whitespace, appearing within either string, is collapsed to a single space before comparison</li>
     * </ul>
     * For example:
     * <pre>assertThat("   my\tfoo  bar ", equalToCompressingWhiteSpace(" my  foo bar"))</pre>
     *
     * @param expectedString
     *     the expected value of matched strings
     */
    public static Matcher<String> equalToCompressingWhiteSpace(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.MIX);
    }

    /**
     * different types of generate string matcher according to whitespace type
     * @author Koko
     * @param expectedString
     *      string used to generate matcher
     */
    public static Matcher<String> equalToCompressingSpace(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.SPACE);
    }

    public static Matcher<String> equalToCompressingTab(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.TAB);
    }

    public static Matcher<String> equalToCompressingLineFeed(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.LINE_FEED);
    }

    public static Matcher<String> equalToCompressingFormFeed(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.FORM_FEED);
    }

    public static Matcher<String> equalToCompressingCarriageReturn(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.CARRIAGE_RETURN);
    }

    public static Matcher<String> equalToCompressingMix(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.MIX);
    }

}
