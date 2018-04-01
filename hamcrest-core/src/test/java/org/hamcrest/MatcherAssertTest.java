package org.hamcrest;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public final class MatcherAssertTest {

    @Test public void
    includesDescriptionOfTestedValueInErrorMessage() {
        String expected = "expected";
        String actual = "actual";

        String expectedMessage = "identifier\nExpected: \"expected\"\n     but: was \"actual\"";

        try {
            assertThat("identifier", actual, equalTo(expected));
        }
        catch (AssertionError e) {
            assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
        }

        fail("should have failed");
    }

    @Test public void
    descriptionCanBeElided() {
        String expected = "expected";
        String actual = "actual";

        String expectedMessage = "\nExpected: \"expected\"\n     but: was \"actual\"";

        try {
            assertThat(actual, equalTo(expected));
        }
        catch (AssertionError e) {
            assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
        }

        fail("should have failed");
    }

    @Test public void
    canTestBooleanDirectly() {
        assertThat("success reason message", true);

        try {
            assertThat("failing reason message", false);
        }
        catch (AssertionError e) {
            assertEquals("failing reason message", e.getMessage());
            return;
        }

        fail("should have failed");
    }

    @Test public void
    includesMismatchDescription() {
        Matcher<String> matcherWithCustomMismatchDescription = new BaseMatcher<String>() {
            @Override
            public boolean matches(String item) {
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Something cool");
            }

            @Override
            public void describeMismatch(String item, Description mismatchDescription) {
                mismatchDescription.appendText("Not cool");
            }
        };

        String expectedMessage = "\nExpected: Something cool\n     but: Not cool";

        try {
            assertThat("Value", matcherWithCustomMismatchDescription);
            fail("should have failed");
        }
        catch (AssertionError e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test public void
    canAssertSubtypes() {
        assertThat(1, equalTo((Number) 1));
    }
}
