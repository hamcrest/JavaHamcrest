package org.hamcrest.exception;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.exception.ThrowsException.throwsException;

public class ThrowsExceptionEqualTest extends AbstractMatcherTest {

    private Runnable runnableThrowing(String message) {
        return new ThrowingRunnable(new IllegalArgumentException(message));
    }

    private Runnable runnableThrowing(Throwable exception) {
        return new ThrowingRunnable(exception);
    }

    @Override
    protected Matcher<?> createMatcher() {
        return throwsException(new IllegalArgumentException("Boom!"));
    }

    public void testEvaluatesToTrueIfRunnableThrowsExpectedExceptionWithMatchingMessage() {
        Matcher<Runnable> matcher = throwsException(new IllegalArgumentException("Boom!"));

        assertMatches(matcher, runnableThrowing("Boom!"));
        assertDoesNotMatch(matcher, runnableThrowing("Bang!"));
        assertMismatchDescription("threw \"java.lang.IllegalArgumentException\" with message \"Bang!\" instead of \"Boom!\"", matcher, runnableThrowing("Bang!"));
        assertDoesNotMatch(matcher, runnableThrowing(new NullPointerException("Boom!")));
        assertMismatchDescription("threw a \"java.lang.NullPointerException\" instead of a \"java.lang.IllegalArgumentException\" exception", matcher, runnableThrowing(new NullPointerException("Boom!")));
    }

    public void testEvaluatesToTrueIfRunnableThrowsExceptionExtendingTheExpectedExceptionWithMatchingMessage() {
        Matcher<Runnable> matcher = throwsException(new IllegalArgumentException("Boom!"));

        assertMatches(matcher, runnableThrowing(new TestException("Boom!")));
    }

    public static class TestException extends IllegalArgumentException {
        public TestException(String message) {
            super(message);
        }
    }

    static class ThrowingRunnable implements Runnable {
        private final Throwable throwable;

        ThrowingRunnable(Throwable throwable) {
            this.throwable = throwable;
        }

        @Override
        public void run() {
            sneakyThrow(throwable);
        }

        @SuppressWarnings("unchecked")
        private <T extends Throwable> void sneakyThrow(Throwable throwable) throws T {
            throw (T) throwable;
        }
    }
}