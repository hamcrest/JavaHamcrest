package org.hamcrest;

import org.junit.Test;

import static org.hamcrest.MatcherExecute.FailStrategy;
import static org.hamcrest.MatcherExecute.failWithAssertionError;
import static org.hamcrest.MatcherExecute.match;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class MatcherExecuteTest {
    private static final class MatcherExecuteTestException extends RuntimeException {
        private MatcherExecuteTestException(final String message) {
            super(message);
        }
    }

    private static final class MatcherExecuteTestFailStrategy implements FailStrategy {
        @Override
        public void fail(final String message) {
            throw new MatcherExecuteTestException(message);
        }
    }

    @Test
    public void
    includesDescriptionOfTestedValueInErrorMessage() {
        String expected = "expected";
        String actual = "actual";

        String expectedMessage = "identifier\nExpected: \"expected\"\n     but: was \"actual\"";

        try {
            match("identifier", actual, equalTo(expected), new MatcherExecuteTestFailStrategy());
        } catch (MatcherExecuteTestException e) {
            assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
        }

        fail("should have failed");
    }

    @Test
    public void
    descriptionCanBeElided() {
        String expected = "expected";
        String actual = "actual";

        String expectedMessage = "\nExpected: \"expected\"\n     but: was \"actual\"";

        try {
            match(actual, equalTo(expected), new MatcherExecuteTestFailStrategy());
        } catch (MatcherExecuteTestException e) {
            assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
        }

        fail("should have failed");
    }

    @Test
    public void
    includesMismatchDescription() {
        Matcher<String> matcherWithCustomMismatchDescription = new BaseMatcher<String>() {
            @Override
            public boolean matches(Object item) {
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Something cool");
            }

            @Override
            public void describeMismatch(Object item, Description mismatchDescription) {
                mismatchDescription.appendText("Not cool");
            }
        };

        String expectedMessage = "\nExpected: Something cool\n     but: Not cool";

        try {
            match("Value", matcherWithCustomMismatchDescription, new MatcherExecuteTestFailStrategy());
            fail("should have failed");
        } catch (MatcherExecuteTestException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Test
    public void
    canMatchSubtypes() {
        final Integer aSub = 1;
        final Number aSuper = aSub;

        match(aSub, equalTo(aSuper), new MatcherExecuteTestFailStrategy());
    }

    @Test(expected = AssertionError.class)
    public void
    canThrowAssertionErrors() {
        String expected = "expected";
        String actual = "actual";

        match(actual, equalTo(expected), failWithAssertionError());
    }
}
