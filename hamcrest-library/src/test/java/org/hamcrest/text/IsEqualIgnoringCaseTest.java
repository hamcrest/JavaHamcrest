package org.hamcrest.text;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public final class IsEqualIgnoringCaseTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = equalToIgnoringCase("irrelevant");
        
        assertNullSafe(matcher);
    }

    @Test public void
    ignoresCaseOfCharsInString() {
        final Matcher<String> matcher = equalToIgnoringCase("heLLo");
		
        assertMatches(matcher, "HELLO");
        assertMatches(matcher, "hello");
        assertMatches(matcher, "HelLo");
    	assertDoesNotMatch(matcher, "bye");
    }

    @Test public void 
    mismatchesIfAdditionalWhitespaceIsPresent() {
    	final Matcher<String> matcher = equalToIgnoringCase("heLLo");
		
    	assertDoesNotMatch(matcher, "hello ");
    	assertDoesNotMatch(matcher, " hello");
    }

    @Test public void 
    mismatchesNull() {
    	final Matcher<String> matcher = equalToIgnoringCase("heLLo");
		
    	assertDoesNotMatch(matcher, null);
    }

    @Test(expected=IllegalArgumentException.class) public void
    canOnlyBeConstructedAboutANonNullString() {
        equalToIgnoringCase(null);
    }


    @Test public void
    describesItself() {
    	final Matcher<String> matcher = equalToIgnoringCase("heLLo");
        assertDescription("a string equal to \"heLLo\" ignoring case", matcher);
    }

    @Test public void
    describesAMismatch() {
    	final Matcher<String> matcher = equalToIgnoringCase("heLLo");
    	String expectedMismatchString = "was \"Cheese\"";
        assertMismatchDescription(expectedMismatchString, matcher, "Cheese");
    }
}
