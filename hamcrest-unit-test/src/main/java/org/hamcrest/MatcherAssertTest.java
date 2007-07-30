package org.hamcrest;

import junit.framework.TestCase;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MatcherAssertTest extends TestCase {
    public void testIncludesDescriptionOfTestedValueInErrorMessage() {
        String expected = "expected";
        String actual = "actual";
        
        String expectedMessage = "identifier\nExpected: \"expected\"\n     got: \"actual\"\n";
        
        try {
            assertThat("identifier", actual, equalTo(expected));
        }
        catch (AssertionError e) {
            assertEquals(expectedMessage, e.getMessage());
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
            assertEquals(expectedMessage, e.getMessage());
            return;
        }
        
        fail("should have failed");
    }
    
    public void testCanTestBooleanDirectly() {
    	assertThat("reason message", true);
    	
    	try {
    		assertThat("failing reason message", false);
    	}
    	catch (AssertionError e) {
    		assertEquals("failing reason message", e.getMessage());
    		return;
    	}
    	
    	fail("should have failed");
    }
}
