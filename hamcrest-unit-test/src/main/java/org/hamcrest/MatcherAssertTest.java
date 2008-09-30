package org.hamcrest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import junit.framework.TestCase;

public class MatcherAssertTest extends TestCase {

    public void testIncludesDescriptionOfTestedValueInErrorMessage() {
        String expected = "expected";
        String actual = "actual";

        String expectedMessage = "identifier\nExpected: \"expected\"\n     got: \"actual\"\n";

        try {
            assertThat("identifier", actual, equalTo(expected));
        }
        catch (AssertionError e) {
            assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
        }

        fail("should have failed");
    }

    public void testDescriptionCanBeElided() {
        String expected = "expected";
        String actual = "actual";

        String expectedMessage = "\nExpected: \"expected\"\n     got: \"actual\"\n";

        try {
            assertThat(actual, equalTo(expected));
        }
        catch (AssertionError e) {
            assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
        }

        fail("should have failed");
    }

    public void testCanTestBooleanDirectly() {
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

    public void testIncludesMismatchDescription() {
      Matcher<String> matcherWithCustomMismatchDescription = new BaseMatcher<String>() {
        public boolean matches(Object item) {
          return false;
        }

        public void describeTo(Description description) {
          description.appendText("Something cool");
        }

        @Override
        public void describeMismatch(Object item, Description mismatchDescription) {
          mismatchDescription.appendText("Not cool");
        }
      };

      String expectedMessage = "\nExpected: Something cool\n     got: \"Value\"\nmismatch: Not cool";

      try {
        assertThat("Value", matcherWithCustomMismatchDescription);
        fail("should have failed");
      }
      catch (AssertionError e) {
        assertEquals(expectedMessage, e.getMessage());
      }
    }
}
