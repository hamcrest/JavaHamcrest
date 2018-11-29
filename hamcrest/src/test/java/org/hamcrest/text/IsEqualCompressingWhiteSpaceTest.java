package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.text.IsEqualCompressingWhiteSpace.equalToCompressingWhiteSpace;

public class IsEqualCompressingWhiteSpaceTest extends AbstractMatcherTest {

    private final Matcher<String> matcher = equalToCompressingWhiteSpace(" Hello World   how\n are we? ");

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
}
