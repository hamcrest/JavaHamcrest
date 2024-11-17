package org.hamcrest.exception;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsInstanceOf;

import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Tests if a Runnable throws a matching exception.
 *
 * @param <T> the type of the matched Runnable
 */
public class ThrowsException<T extends Runnable> extends TypeSafeDiagnosingMatcher<T> {
  private final IsInstanceOf classMatcher;
  private final Matcher<? super String> messageMatcher;

  /**
   * Constructor, best called from one of the static {@link #throwsException()} methods.
   * @param classMatcher the matcher for the type of the exception
   * @param messageMatcher the matcher for the exception message
   */
  public ThrowsException(IsInstanceOf classMatcher, Matcher<? super String> messageMatcher) {
    this.classMatcher = classMatcher;
    this.messageMatcher = messageMatcher;
  }

  /**
   * Matcher for {@link Runnable} that expects an exception to be thrown
   *
   * @param <T> type of the Runnable
   * @return The matcher.
   */
  public static <T extends Runnable> Matcher<T> throwsException() {
    return throwsException(Throwable.class);
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception equal
   * to the provided <code>throwable</code>
   *
   * @param <U>       type of the Runnable
   * @param <T>       type of the Throwable
   * @param throwable the Throwable class against which examined exceptions are compared
   * @return The matcher.
   */
  public static <T extends Runnable, U extends Throwable> Matcher<T> throwsException(U throwable) {
    return throwsException(throwable.getClass(), throwable.getMessage());
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception of the
   * provided <code>throwableClass</code> class
   *
   * @param <U>            type of the Runnable
   * @param <T>            type of the Throwable
   * @param throwableClass the Throwable class against which examined exceptions are compared
   * @return The matcher.
   */
  public static <T extends Runnable, U extends Throwable> Matcher<T> throwsException(Class<U> throwableClass) {
    return new ThrowsException<>(new IsInstanceOf(throwableClass), anything("<anything>"));
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception of the
   * provided <code>throwableClass</code> class and has a message equal to the provided
   * <code>message</code>
   *
   * @param <T>            type of the Runnable
   * @param <U>            type of the Throwable
   * @param throwableClass the Throwable class against which examined exceptions are compared
   * @param exactMessage   the String against which examined exception messages are compared
   * @return The matcher.
   */
  public static <T extends Runnable, U extends Throwable> Matcher<T> throwsException(Class<U> throwableClass, String exactMessage) {
    return throwsException(throwableClass, equalTo(exactMessage));
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception of the provided
   * <code>throwableClass</code> class and has a message matching the provided
   * <code>messageMatcher</code>
   *
   * @param <T>            type of the Runnable
   * @param <U>            type of the Throwable
   * @param throwableClass the Throwable class against which examined exceptions are compared
   * @param messageMatcher matcher to validate exception's message
   * @return The matcher.
   */
  public static <T extends Runnable, U extends Throwable> Matcher<T> throwsException(Class<U> throwableClass, Matcher<String> messageMatcher) {
    return new ThrowsException<>(new IsInstanceOf(throwableClass), messageMatcher);
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception with a message equal to the provided <code>message</code>
   *
   * @param <T>          type of the Runnable
   * @param exactMessage the String against which examined exception messages are compared
   * @return The matcher.
   */
  public static <T extends Runnable> Matcher<T> throwsExceptionWithMessage(String exactMessage) {
    return throwsException(Throwable.class, equalTo(exactMessage));
  }

  /**
   * Matcher for {@link Throwable} that expects that the Runnable throws an exception with a message matching the provided <code>messageMatcher</code>
   *
   * @param <T>            type of the Runnable
   * @param messageMatcher matcher to validate exception's message
   * @return The matcher.
   */
  public static <T extends Runnable> Matcher<T> throwsExceptionWithMessage(Matcher<String> messageMatcher) {
    return throwsException(Throwable.class, messageMatcher);
  }

  @Override
  protected boolean matchesSafely(T runnable, Description mismatchDescription) {
    try {
      runnable.run();
      mismatchDescription.appendText("the runnable didn't throw");
      return false;
    } catch (Throwable t) {
      boolean classMatches = classMatcher.matches(t);
      if (!classMatches) {
        mismatchDescription.appendText("thrown exception class was ").appendText(t.getClass().getName());
      }

      boolean messageMatches = messageMatcher.matches(t.getMessage());
      if (!messageMatches) {
        if (!classMatches) {
          mismatchDescription.appendText(" and the ");
        }
        mismatchDescription.appendText("thrown exception message ");
        messageMatcher.describeMismatch(t.getMessage(), mismatchDescription);
      }

      return classMatches && messageMatches;
    }
  }

  @Override
  public void describeTo(Description description) {
    description
        .appendText("a runnable throwing ").appendDescriptionOf(classMatcher)
        .appendText(" with message ").appendDescriptionOf(messageMatcher);
  }
}
