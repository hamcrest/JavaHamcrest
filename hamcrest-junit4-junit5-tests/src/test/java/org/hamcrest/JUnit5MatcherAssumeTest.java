package org.hamcrest;

import org.junit.AssumptionViolatedException;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;

import static org.hamcrest.MatcherAssume.assumeThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests compatibility with JUnit 5 with JUnit 4 <i>and</i> JUnit 5 on the classpath.
 * The equivalent test with only JUnit 4 on the classpath is in another module.
 */
class JUnit5MatcherAssumeTest {

    @Test
    void
    assumptionFailsWithMessage() {
        try {
            assumeThat("Custom assumption", "a", startsWith("abc"));
            fail("should have failed");
        }
        catch (TestAbortedException e) {
            assertEquals("Assumption failed: Custom assumption", e.getMessage());
        }
        catch (AssumptionViolatedException e) {
            // If we don't catch JUnit 4 exceptions here, then this test will result in a false positive, or actually
            // a false ignored test.
            throw new AssertionError("Illegal JUnit 4 assumption", e);
        }
    }

    @Test void
    assumptionFailsWithDefaultMessage() {
        try {
            assumeThat("a", startsWith("abc"));
            fail("should have failed");
        }
        catch (TestAbortedException e) {
            assertEquals("Assumption failed", e.getMessage());
        }
        catch (AssumptionViolatedException e) {
            // If we don't catch JUnit 4 exceptions here, then this test will result in a false positive, or actually
            // a false ignored test.
            throw new AssertionError("Illegal JUnit 4 assumption", e);
        }
    }

    @Test void
    assumptionSucceeds() {
        try {
            assumeThat("xyz", startsWith("xy"));
        }
        catch (AssumptionViolatedException e) {
            // If we don't catch JUnit 4 exceptions here, then this test will result in a false positive, or actually
            // a false ignored test.
            throw new AssertionError("Illegal JUnit 4 assumption", e);
        }
    }
}
