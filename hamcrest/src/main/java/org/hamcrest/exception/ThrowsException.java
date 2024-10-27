package org.hamcrest.exception;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.exception.ThrowsExceptionEqualTo.exceptionEqualTo;
import static org.hamcrest.exception.ThrowsExceptionWithMessage.withMessage;

/**
 * Tests if a Runnable throws a matching exception.
 *
 * @param <T> the type of the matched Runnable
 */
public class ThrowsException<T extends Runnable> extends TypeSafeMatcher<T> {

  private final Matcher<? super Throwable> exceptionMatcher;
  private Throwable actualException;

  /**
   * Constructor, best called from one of the static factory methods.
   *
   * @param elementMatcher matches the expected element
   */
  public ThrowsException(Matcher<? super Throwable> elementMatcher) {
    this.exceptionMatcher = elementMatcher;
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception
   *
   * @param <T> type of the Runnable
   * @return The matcher.
   */
  public static <T extends Runnable> ThrowsException<T> throwsException() {
    return new ThrowsException<>(instanceOf(Throwable.class));
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception equal to the provided <code>throwable</code>
   *
   * @param <T>       type of the Runnable
   * @param <U>       type of the Throwable
   * @param throwable the Throwable instance against which examined exceptions are compared
   * @return The matcher.
   */
  public static <T extends Runnable, U extends Throwable> ThrowsException<T> throwsException(U throwable) {
    return new ThrowsException<>(exceptionEqualTo(throwable));
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception of the provided <code>throwableClass</code> class
   *
   * @param <U>            type of the Runnable
   * @param <T>            type of the Throwable
   * @param throwableClass the Throwable class against which examined exceptions are compared
   * @return The matcher.
   */
  public static <T extends Runnable, U extends Throwable> ThrowsException<T> throwsException(Class<U> throwableClass) {
    return new ThrowsException<>(instanceOf(throwableClass));
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception with a message equal to the provided <code>message</code> class
   *
   * @param <T>     type of the Runnable
   * @param message the String against which examined exception messages are compared
   * @return The matcher.
   */
  public static <T extends Runnable> ThrowsException<T> throwsException(String message) {
    return new ThrowsException<>(withMessage(message));
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception matching provided <code>matcher</code>
   *
   * @param <T>     type of the Runnable
   * @param matcher matcher to validate the exception
   * @return The matcher.
   */
  public static <T extends Runnable> ThrowsException<T> throwsException(Matcher<? super Throwable> matcher) {
    return new ThrowsException<>(matcher);
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception of the provided <code>throwableClass</code> class and has a message equal to the provided <code>message</code>
   *
   * @param <T>            type of the Runnable
   * @param <U>            type of the Throwable
   * @param throwableClass the Throwable class against which examined exceptions are compared
   * @param message        the String against which examined exception messages are compared
   * @return The matcher.
   */
  public static <T extends Runnable, U extends Throwable> ThrowsException<T> throwsException(Class<U> throwableClass, String message) {
    return new ThrowsException<>(allOf(instanceOf(throwableClass), withMessage(message)));
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception of the provided <code>throwableClass</code> class and matches the provided <code>matcher</code>
   *
   * @param <U>            type of the Runnable
   * @param <T>            type of the Throwable
   * @param throwableClass the Throwable class against which examined exceptions are compared
   * @param matcher        matcher to validate the exception
   * @return The matcher.
   */
  public static <T extends Runnable, U extends Throwable> ThrowsException<T> throwsException(Class<U> throwableClass, Matcher<? super Throwable> matcher) {
    return new ThrowsException<>(allOf(instanceOf(throwableClass), matcher));
  }

  @Override
  public boolean matchesSafely(T runnable) {
    try {
      runnable.run();
      return false;
    } catch (Throwable t) {
      actualException = t;
      return exceptionMatcher.matches(t);
    }
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("a runnable throwing ").appendDescriptionOf(exceptionMatcher);
  }

  @Override
  public void describeMismatchSafely(T runnable, Description mismatchDescription) {
    if (actualException == null) {
      mismatchDescription.appendText("the runnable didn't throw");
      return;
    }
    mismatchDescription.appendText("the runnable threw ");
    exceptionMatcher.describeMismatch(actualException, mismatchDescription);
  }
}
