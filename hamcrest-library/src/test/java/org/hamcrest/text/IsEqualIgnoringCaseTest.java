/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

import org.hamcrest.Matcher;
import org.junit.Test;

public final class IsEqualIgnoringCaseTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = equalToIgnoringCase("irrelevant");
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    ignoresCaseOfCharsInString() {
        final Matcher<String> matcher = equalToIgnoringCase("heLLo");
		
        assertMatches("didn't match", matcher, "HELLO");
        assertMatches("didn't match", matcher, "hello");
        assertMatches("didn't match", matcher, "HelLo");
    	assertDoesNotMatch("matched unexpectedly", matcher, "bye");
    }

    @Test public void 
    mismatchesIfAdditionalWhitespaceIsPresent() {
    	final Matcher<String> matcher = equalToIgnoringCase("heLLo");
		
    	assertDoesNotMatch("matched unexpectedly", matcher, "hello ");
    	assertDoesNotMatch("matched unexpectedly", matcher, " hello");
    }

    @Test public void 
    mismatchesNull() {
    	final Matcher<String> matcher = equalToIgnoringCase("heLLo");
		
    	assertDoesNotMatch("matched unexpectedly", matcher, null);
    }

    @Test(expected=IllegalArgumentException.class) public void
    canOnlyBeConstructedAboutANonNullString() {
        equalToIgnoringCase(null);
    }


    @Test public void
    describesItself() {
    	final Matcher<String> matcher = equalToIgnoringCase("heLLo");
        assertDescription("equalToIgnoringCase(\"heLLo\")", matcher);
    }

    @Test public void
    describesAMismatch() {
    	final Matcher<String> matcher = equalToIgnoringCase("heLLo");
    	String expectedMismatchString = "was \"Cheese\"";
        assertMismatchDescription(expectedMismatchString, matcher, "Cheese");
    }
}
