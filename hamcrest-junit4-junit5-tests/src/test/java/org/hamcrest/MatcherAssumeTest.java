package org.hamcrest;

import org.junit.AssumptionViolatedException;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.hamcrest.MatcherAssume.assumeThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

class MatcherAssumeTest {

    @Test
    void assumptionFailsWithAssertionErrorWhenNoJUnitInStackTrace() throws Throwable {
        // Run the assumption on a separate thread to make sure it has JUnit 4 nor JUnit 5 in its stack trace.
        ExecutorService executor = newSingleThreadExecutor();
        try {
            try {
                executor.submit(new Runnable() {

                    @Override
                    public void run() {
                        assumeThat(1, is(2));
                    }
                }).get();
                fail("Expected " + ExecutionException.class);
            } catch (ExecutionException expected) {
                throw expected.getCause();
            }
        } catch (AssertionError expected) {
        } catch (TestAbortedException | AssumptionViolatedException e) {
            throw new AssertionError(e);
        }
    }
}