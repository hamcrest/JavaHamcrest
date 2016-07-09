package org.hamcrest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;
import static org.hamcrest.core.IsEqual.equalTo;

public class MultiMatcherAssertTest {
    @Test public void
    testSingleAssertFail() {
    	String reason = "reason";
    	String actual = "actual";
    	String expected = "expected";

		String expectedMessage = "\n[1] reason\nExpected: \"expected\"\n     but: was \"actual\"";
		
    	try {
		    MatcherMultiAssert.assertThat(reason, actual, equalTo(expected)).resolve();
    	} catch(AssertionError e) {
			assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
    	}

        fail("should have failed");
    }
    
    @Test public void
    testMultipleAssertFail() {
    	String reason = "reason";
    	String actual = "actual";
    	String expected = "expected";

		String expectedMessage = "\n[1] reason\nExpected: \"expected\"\n     but: was \"actual\"\n[2] reason\nExpected: \"expected\"\n     but: was \"actual\"\n[3] \nExpected: \"expected\"\n     but: was \"actual\"\n[4] reason\nExpected: is <true>\n     but: was <false>\n";
		
    	try {
		    MatcherMultiAssert
		    .assertThat(reason, actual, equalTo(expected))
		    .andThat(reason, actual, equalTo(expected))
		    .andThat(actual, equalTo(expected))
		    .andThat(reason, false)
		    .resolve();
    	} catch(AssertionError e) {
    		assertThat(e.getMessage(), equalTo(expectedMessage));
			assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
    	}

        fail("should have failed");
    }
    
    
    @Test public void
    testMultipleAssertMixed() {
    	String reason = "reason";
    	String actual = "actual";
    	String expected = "expected";

		String expectedMessage = "\n[1] reason\nExpected: \"expected\"\n     but: was \"actual\"\n[3] \nExpected: \"expected\"\n     but: was \"actual\"\n[4] reason\nExpected: is <true>\n     but: was <false>\n[5] reason\nExpected: \"expected\"\n     but: was \"actual\"\n";
		
    	try {
		    MatcherMultiAssert
		    .assertThat(reason, actual, equalTo(expected))
		    .andThat(reason, actual, equalTo(actual))
		    .andThat(actual, equalTo(expected))
		    .andThat(reason, false)
		    .andThat(reason, actual, equalTo(expected))
		    .andThat(actual, equalTo(actual))
		    .andThat(reason, true)
		    .resolve();
    	} catch(AssertionError e) {
    		assertThat(e.getMessage(), equalTo(expectedMessage));
			assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
    	}

        fail("should have failed");
    }
    
    @Test public void
    testSingleAssertSuccess() {
    	String reason = "reason";
    	String actual = "actual";
    	String expected = "actual";

		MatcherMultiAssert.assertThat(reason, actual, equalTo(expected)).resolve();
    }
    
    @Test public void
    testMultipleAssertSuccess() {
    	String reason = "reason";
    	String actual = "actual";
    	String expected = "actual";

		MatcherMultiAssert
		.assertThat(reason, actual, equalTo(expected))
		.andThat(reason, true)
		.andThat(reason, actual, equalTo(expected))
		.andThat(actual, equalTo(expected))
		.resolve();
    }
}
