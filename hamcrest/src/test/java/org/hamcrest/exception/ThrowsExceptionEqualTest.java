package org.hamcrest.exception;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.exception.ThrowsException.throwsException;
import static org.hamcrest.exception.ThrowsExceptionWithMessage.withMessage;

public class ThrowsExceptionEqualTest extends AbstractMatcherTest {

    private Runnable runnableThrowing(Throwable exception) {
        return new ThrowingRunnable(exception);
    }

    @Override
    protected Matcher<?> createMatcher() {
        return throwsException(IllegalArgumentException.class);
    }

    public void testExamples() {
        RuntimeException boom = new RuntimeException("boom");
        Runnable codeThatThrows = () -> {
            throw boom;
        };

        assertThat(codeThatThrows, throwsException());
        assertThat(codeThatThrows, throwsException(boom));
        assertThat(codeThatThrows, throwsException(RuntimeException.class));
        assertThat(codeThatThrows, throwsException(withMessage("boom")));
        assertThat(codeThatThrows, throwsException(withMessage(containsString("oom"))));
        assertThat(codeThatThrows, throwsException(RuntimeException.class, "boom"));
        assertThat(codeThatThrows, throwsException(RuntimeException.class, withMessage("boom")));
        assertThat(codeThatThrows, throwsException(RuntimeException.class, withMessage(containsString("boom"))));
    }

    public void testEvaluatesToTrueIfRunnableThrowsExpectedExceptionWithMatchingMessage() {
        assertMatches(
                throwsException(new IllegalArgumentException("Boom!")),
                runnableThrowing(new IllegalArgumentException("Boom!"))
        );

        assertDescription(
                "a runnable throwing \"java.lang.IllegalArgumentException\" with message \"Boom!\"",
                throwsException(new IllegalArgumentException("Boom!"))
        );

        assertMismatchDescription(
                "the runnable threw \"java.lang.IllegalArgumentException\" with message \"Bang!\" instead of \"Boom!\"",
                throwsException(new IllegalArgumentException("Boom!")),
                runnableThrowing(new IllegalArgumentException("Bang!"))
        );

        assertMismatchDescription(
                "the runnable threw \"java.lang.NullPointerException\" instead of a \"java.lang.IllegalArgumentException\" exception",
                throwsException(new IllegalArgumentException("Boom!")),
                runnableThrowing(new NullPointerException("Boom!"))
        );

        assertMismatchDescription(
                "the runnable didn't throw",
                throwsException(new IllegalArgumentException("Boom!")),
                (Runnable) () -> {
                }
        );
    }

    public void testEvaluatesToTrueIfRunnableThrowsExceptionExtendingTheExpectedExceptionWithMatchingMessage() {
        assertMatches(
                throwsException(new IllegalArgumentException("Boom!")),
                runnableThrowing(new TestException("Boom!"))
        );
    }

    public void testEvaluatesToTrueIfRunnableThrowsExceptionWithExpectedMessage() {
        assertMatches(
                throwsException(withMessage("Boom!")),
                runnableThrowing(new TestException("Boom!"))
        );

        assertDescription(
                "a runnable throwing an exception with message \"Boom!\"",
                throwsException(withMessage("Boom!"))
        );

        assertMismatchDescription(
                "the runnable threw an exception with message \"Bang!\" instead of \"Boom!\"",
                throwsException(withMessage("Boom!")),
                runnableThrowing(new IllegalArgumentException("Bang!"))
        );
    }

    public void testEvaluatesToTrueIfRunnableThrowsExceptionWithMatchingMessage() {

        assertMatches(
                throwsException(withMessage(containsString("bar"))),
                runnableThrowing(new TestException("Foo bar baz"))
        );

        assertDescription(
                "a runnable throwing an exception with message a string containing \"bar\"",
                throwsException(withMessage(containsString("bar")))
        );

        assertMismatchDescription(
                "the runnable threw an exception with message \"Bang!\" instead of a string containing \"bar\"",
                throwsException(withMessage(containsString("bar"))),
                runnableThrowing(new IllegalArgumentException("Bang!"))
        );
    }

    public void testEvaluatesToTrueIfRunnableThrowsMatchingException() {
        assertMatches(
                throwsException(instanceOf(TestException.class)),
                runnableThrowing(new TestException("Boom!"))
        );

        assertDescription(
                "a runnable throwing an instance of java.lang.NullPointerException",
                throwsException(instanceOf(NullPointerException.class))
        );

        assertMismatchDescription(
                "the runnable threw <java.lang.IllegalArgumentException: Bang!> is a java.lang.IllegalArgumentException",
                throwsException(instanceOf(NullPointerException.class)),
                runnableThrowing(new IllegalArgumentException("Bang!"))
        );
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