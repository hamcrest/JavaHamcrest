package org.hamcrest.exception;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.throwsExceptionWithMessage;
import static org.hamcrest.exception.ThrowsException.throwsException;
import static org.hamcrest.test.MatcherAssertions.*;

public final class ThrowsExceptionTest {

  public static void throwIllegalArgumentException() {
    throw new IllegalArgumentException("Boom!");
  }

  public static void throwNullPointerException() {
    throw new NullPointerException("Boom!");
  }

  @Test
  public void examples() {
    assertThat(ThrowsExceptionTest::throwIllegalArgumentException, throwsException());
    assertThat(ThrowsExceptionTest::throwIllegalArgumentException, throwsException(RuntimeException.class));
    assertThat(ThrowsExceptionTest::throwIllegalArgumentException, throwsException(RuntimeException.class, "Boom!"));
    assertThat(ThrowsExceptionTest::throwIllegalArgumentException, throwsException(new IllegalArgumentException("Boom!")));
    assertThat(ThrowsExceptionTest::throwIllegalArgumentException, throwsException(RuntimeException.class, containsString("Boo")));
    assertThat(ThrowsExceptionTest::throwIllegalArgumentException, throwsExceptionWithMessage("Boom!"));
    assertThat(ThrowsExceptionTest::throwIllegalArgumentException, throwsExceptionWithMessage(containsString("Boo")));
  }

  @Test
  public void evaluatesToTrueIfRunnableThrowsExpectedExceptionWithMatchingMessage() {
    assertMatches(
        throwsException(IllegalArgumentException.class, "Boom!"),
        ThrowsExceptionTest::throwIllegalArgumentException
    );

    assertDescription(
        "a runnable throwing an instance of java.lang.IllegalArgumentException with message \"Boom!\"",
        throwsException(IllegalArgumentException.class, "Boom!")
    );

    assertMismatchDescription(
        "thrown exception message was \"Boom!\"",
        throwsException(IllegalArgumentException.class, "Bang!"),
        (Runnable) ThrowsExceptionTest::throwIllegalArgumentException
    );

    assertMismatchDescription(
        "thrown exception class was java.lang.NullPointerException",
        throwsException(IllegalArgumentException.class, "Boom!"),
        (Runnable) ThrowsExceptionTest::throwNullPointerException
    );

    assertMismatchDescription(
        "the runnable didn't throw",
        throwsException(IllegalArgumentException.class, "Boom!"),
        (Runnable) () -> {
        }
    );
  }

  @Test
  public void evaluatesToTrueIfRunnableThrowsExceptionExtendingTheExpectedExceptionWithMatchingMessage() {
    assertMatches(
        throwsException(IllegalArgumentException.class, "Boom!"),
        ThrowsExceptionTest::throwIllegalArgumentException
    );
  }

  @Test
  public void evaluatesToTrueIfRunnableThrowsExceptionWithMatchingMessage() {
    assertMatches(
        throwsException(IllegalArgumentException.class, containsString("Boo")),
        ThrowsExceptionTest::throwIllegalArgumentException
    );

    assertDescription(
        "a runnable throwing an instance of java.lang.IllegalArgumentException with message a string containing \"Boo\"",
        throwsException(IllegalArgumentException.class, containsString("Boo"))
    );

    assertMismatchDescription(
        "thrown exception class was java.lang.NullPointerException",
        throwsException(IllegalArgumentException.class, containsString("Boo")),
        (Runnable) ThrowsExceptionTest::throwNullPointerException
    );
  }
}
