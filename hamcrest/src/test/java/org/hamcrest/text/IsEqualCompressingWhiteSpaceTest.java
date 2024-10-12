package org.hamcrest.text;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.text.IsEqualCompressingWhiteSpace.equalToCompressingWhiteSpace;

public class IsEqualCompressingWhiteSpaceTest extends AbstractMatcherTest {

    private final Matcher<String> matcher = equalToCompressingWhiteSpace(" Hello World   how\n are we? ");

    @Override
    protected Matcher<?> createMatcher() {
        return matcher;
    }

    @Test
    public void testPassesIfWordsAreSameButWhitespaceDiffers() {
        assertMatches(matcher, "Hello World how are we?");
        assertMatches(matcher, "   Hello World   how are \n\n\twe?");
    }

    @Test
    public void testFailsIfTextOtherThanWhitespaceDiffers() {
        assertDoesNotMatch(matcher, "Hello PLANET how are we?");
        assertDoesNotMatch(matcher, "Hello World how are we");
    }

    @Test
    public void testFailsIfWhitespaceIsAddedOrRemovedInMidWord() {
        assertDoesNotMatch(matcher, "HelloWorld how are we?");
        assertDoesNotMatch(matcher, "Hello Wo rld how are we?");
    }

    @Test
    public void test_has_a_readable_mismatch() {
        assertMismatchDescription("was \"Hello World how are we \"", matcher, "Hello World how are we ");
    }

    @Test
    public void testFailsIfMatchingAgainstNull() {
        assertDoesNotMatch(matcher, null);
    }

    @Test
    public void testHasAReadableDescription() {
        assertDescription("a string equal to \" Hello World   how\\n are we? \" compressing white space",
                        matcher);
    }

    @Test
    public void testPassesIfWhitespacesContainsNoBreakSpace() {
        assertMatches(matcher, "Hello" + ((char)160) + "World how are we?");
    }

}
