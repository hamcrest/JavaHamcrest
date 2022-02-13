package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.text.IsEqualCompressingWhiteSpace.*;

public class IsEqualCompressingWhiteSpaceTest extends AbstractMatcherTest {

    private final Matcher<String> matcher = equalToCompressingMix(" Hello World   how\n are we? ");
    private final Matcher<String> SpaceMatcher = equalToCompressingSpace(" Hello World   how       are we? ");
    private final Matcher<String> TabMatcher = equalToCompressingTab(" Hello World   how\t are\t\t we? ");
    private final Matcher<String> LineFeedMatcher = equalToCompressingLineFeed(" Hello World   how\n are we? ");
    private final Matcher<String> FormFeedMatcher = equalToCompressingFormFeed(" Hello World   how\f are\f\f we? ");
    private final Matcher<String> CarriageReturnMatcher = equalToCompressingCarriageReturn(" Hello \r World   how \r\r are we? ");

    @Override
    protected Matcher<?> createMatcher() {
        return matcher;
    }

    public void testPassesIfWordsAreSameButWhitespaceDiffers() {
        assertMatches(matcher, "Hello World how are we?");
        assertMatches(matcher, "   Hello World   how are \n\n\twe?");
    }

    public void testFailsIfTextOtherThanWhitespaceDiffers() {
        assertDoesNotMatch(matcher, "Hello PLANET how are we?");
        assertDoesNotMatch(matcher, "Hello World how are we");
    }

    public void testFailsIfWhitespaceIsAddedOrRemovedInMidWord() {
        assertDoesNotMatch(matcher, "HelloWorld how are we?");
        assertDoesNotMatch(matcher, "Hello Wo rld how are we?");
    }

    public void test_has_a_readable_mismatch() {
        assertMismatchDescription("was \"Hello World how are we \"", matcher, "Hello World how are we ");
    }

    public void testFailsIfMatchingAgainstNull() {
        assertDoesNotMatch(matcher, null);
    }

    public void testHasAReadableDescription() {
        assertDescription("a string equal to \" Hello World   how\\n are we? \" compressing white space",
                        matcher);
    }

    public void testPassesIfWhitespacesContainsNoBreakSpace() {
        assertMatches(matcher, "Hello" + ((char)160) + "World how are we?");
    }

    public void testFailsIfwordsAreSameButWhiteSpaceDiffers()
    {
        assertDoesNotMatch(SpaceMatcher, " Hello World   how\n are we? ");
        assertDoesNotMatch(TabMatcher, " Hello World   how\n are we? ");
        assertDoesNotMatch(LineFeedMatcher, " Hello World   how\r are we? ");
    }

    public void testPassesIfwordsAreSameButMixWhiteSpace()
    {
        assertMatches(matcher, "Hello\f\f World\t how      are\r we?\n\n");
    }

    public void testUnitWhiteSpace()
    {
        assertMatches(SpaceMatcher, "  Hello     World    how              are we?   ");
        assertMatches(TabMatcher, " Hello World   how \t are   we? \t");
        assertMatches(LineFeedMatcher, "   Hello World   how\n are  \n we? ");
        assertMatches(FormFeedMatcher, " Hello   World\f   how are we? ");
        assertMatches(CarriageReturnMatcher, "Hello World how are we?");
    }
}
