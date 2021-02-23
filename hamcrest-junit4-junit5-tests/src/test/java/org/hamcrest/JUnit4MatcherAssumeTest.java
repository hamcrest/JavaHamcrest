package org.hamcrest;

import org.junit.Test;
import org.junit.AssumptionViolatedException;
import org.opentest4j.TestAbortedException;

import static org.hamcrest.MatcherAssume.assumeThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests compatibility with JUnit 4 <i>and</i> JUnit 5 on the classpath.
 * The equivalent test with only JUnit 4 on the classpath is in another module.
 */
public class JUnit4MatcherAssumeTest {

    @Test public void
    assumptionFailsWithMessage() {
        try {
            assumeThat("Custom assumption", "a", startsWith("abc"));
            fail("should have failed");
        }
        catch (AssumptionViolatedException e) {
            assertEquals("Custom assumption: got: \"a\", expected: a string starting with \"abc\"", e.getMessage());
        }
        catch (TestAbortedException e) {
            throw new AssertionError("Illegal JUnit 5 assumption", e);
        }
    }

    @Test public void
    assumptionFailsWithDefaultMessage() {
        try {
            assumeThat("a", startsWith("abc"));
            fail("should have failed");
        }
        catch (AssumptionViolatedException e) {
            assertEquals(": got: \"a\", expected: a string starting with \"abc\"", e.getMessage());
        }
        catch (TestAbortedException e) {
            throw new AssertionError("Illegal JUnit 5 assumption", e);
        }
    }

    @Test public void
    assumptionSucceeds() {
        try {
            assumeThat("xyz", startsWith("xy"));
        } catch (TestAbortedException e) {
            throw new AssertionError("Illegal JUnit 5 assumption", e);
        }
    }
}
