package org.hamcrest.exception;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.exception.ThrowsException.throwsException;

public class ThrowsExceptionTest extends AbstractMatcherTest {

    private static Runnable runnableThrowing(String message) {
        return () -> {
            throw new IllegalArgumentException(message);
        };
    }

    private static Runnable runnableThrowing(Class<? extends Throwable> exceptionClass, String message) {
        return () -> {
            try {
                throw exceptionClass.getDeclaredConstructor().newInstance(message);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
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
        assertDoesNotMatch(matcher, runnableThrowing(NullPointerException.class, "Bang!"));
        assertMismatchDescription("threw a \"java.lang.RuntimeException\" instead of a \"java.lang.IllegalArgumentException\" exception", matcher, runnableThrowing(NullPointerException.class, "Bang!"));
    }
}