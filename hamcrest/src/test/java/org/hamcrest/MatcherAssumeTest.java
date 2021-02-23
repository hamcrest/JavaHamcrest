package org.hamcrest;

import org.junit.Test;
import org.opentest4j.TestAbortedException;

import static org.hamcrest.MatcherAssume.assumeThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;

public class MatcherAssumeTest {

    @Test public void
    assumptionFailsWithMessage() {
        try {
            assumeThat("Custom assumption", "a", startsWith("abc"));
            fail("should have failed");
        }
        catch (TestAbortedException e) {
            assertEquals("Assumption failed: Custom assumption", e.getMessage());
        }
    }

    @Test public void
    assumptionFailsWithDefaultMessage() {
        try {
            assumeThat("a", startsWith("abc"));
            fail("should have failed");
        }
        catch (TestAbortedException e) {
            assertEquals("Assumption failed", e.getMessage());
        }
    }

    @Test public void
    assumptionSucceeds() {
        assumeThat("xyz", startsWith("xy"));
    }
}