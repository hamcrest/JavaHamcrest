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

    enum whiteSpaceType
    {
        SPACE, // \s
        TAB,// \t
        LINEFEED,// \n
        FORMFEED, // \f
        CARRIAGERETURN, // \r
        MIX;
    }

    private final String string;
    private final whiteSpaceType type;

    public IsEqualCompressingWhiteSpace(String string, whiteSpaceType type) {
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

    public String stripSpaces(String toBeStripped) {
        if (this.type == whiteSpaceType.TAB){
            return toBeStripped.replaceAll("[\\p{Z}\\t]+", " ").trim();
        }
        else if (this.type == whiteSpaceType.LINEFEED){
            return toBeStripped.replaceAll("[\\p{Z}\\n]+", " ").trim();
        }
        else if (this.type == whiteSpaceType.FORMFEED){
            return toBeStripped.replaceAll("[\\p{Z}\\f]+", " ").trim();
        }
        else if (this.type == whiteSpaceType.CARRIAGERETURN){
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

    public static Matcher<String> equalToCompressingSPACE(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.SPACE);
    }

    public static Matcher<String> equalToCompressingTAB(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.TAB);
    }

    public static Matcher<String> equalToCompressingLINEFEED(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.LINEFEED);
    }

    public static Matcher<String> equalToCompressingFORMFEED(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.FORMFEED);
    }

    public static Matcher<String> equalToCompressingCARRIAGERETURN(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.CARRIAGERETURN);
    }

    public static Matcher<String> equalToCompressingMIX(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString, whiteSpaceType.MIX);
    }

}
