package org.hamcrest.object;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.object.HasToString.hasToString;

import org.hamcrest.Matcher;
import org.junit.Test;

public final class HasToStringTest {
    private static final String TO_STRING_RESULT = "toString result";
    private static final Object TEST_OBJECT = new Object() {
        @Override
        public String toString() {
            return TO_STRING_RESULT;
        }
    };

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<Object> matcher = hasToString(equalTo("irrelevant"));
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }
    
    @Test public void
    matchesWhenUtilisingANestedMatcher() {
    	final Matcher<Object> matcher = hasToString(equalTo(TO_STRING_RESULT));

    	assertMatches(matcher, TEST_OBJECT);
    	assertDoesNotMatch(matcher, new Object());
    }

    @Test public void
    matchesWhenUsingShortcutForHasToStringEqualTo() {
    	final Matcher<Object> matcher = hasToString(TO_STRING_RESULT);
    	
		assertMatches(matcher, TEST_OBJECT);
    	assertDoesNotMatch(matcher, new Object());
    }

    @Test public void
    describesItself() {
    	final Matcher<Object> matcher = hasToString(equalTo(TO_STRING_RESULT));
        assertDescription("with toString() \"toString result\"", matcher);
    }

    @Test public void
    describesAMismatch() {
    	final Matcher<Object> matcher = hasToString(equalTo(TO_STRING_RESULT));
    	String expectedMismatchString = "toString() was \"Cheese\"";
        assertMismatchDescription(expectedMismatchString, matcher, "Cheese");
    }
}
