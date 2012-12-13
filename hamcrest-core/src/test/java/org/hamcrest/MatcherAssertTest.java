package org.hamcrest;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MatcherAssertTest extends TestCase {

    public void testIncludesDescriptionOfTestedValueInErrorMessage() {
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

    public void testDescriptionCanBeElided() {
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
        assertThat("Value", matcherWithCustomMismatchDescription);
        fail("should have failed");
      }
      catch (AssertionError e) {
        assertEquals(expectedMessage, e.getMessage());
      }
    }
    
    
    public void testCanAssertSubtypes() {
      Integer aSub = new Integer(1);
      Number aSuper = aSub;
      
      assertThat(aSub, equalTo(aSuper));
    }
}
