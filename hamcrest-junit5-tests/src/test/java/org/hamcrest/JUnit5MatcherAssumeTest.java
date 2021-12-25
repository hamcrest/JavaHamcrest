package org.hamcrest;

import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;

import static org.hamcrest.MatcherAssume.assumeThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests compatibility with JUnit 5 with only JUnit 5 on the classpath.
 * The equivalent test with JUnit 4 <i>and</i> JUnit 5 on the classpath is in another module.
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
    }

    @Test void
    assumptionSucceeds() {
        assumeThat("xyz", startsWith("xy"));
    }
}
