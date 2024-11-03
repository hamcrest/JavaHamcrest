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

  public ThrowsException(IsInstanceOf classMatcher, Matcher<? super String> messageMatcher) {
    this.classMatcher = classMatcher;
    this.messageMatcher = messageMatcher;
  }

  public static <T extends Runnable> ThrowsException<T> throwsException() {
    return throwsException(Throwable.class);
  }

  public static <T extends Runnable, U extends Throwable> ThrowsException<T> throwsException(Class<U> throwableClass) {
    return new ThrowsException<>(new IsInstanceOf(throwableClass), anything("<anything>"));
  }

  public static <T extends Runnable, U extends Throwable> ThrowsException<T> throwsException(Class<U> throwableClass, String exactMessage) {
    return throwsException(throwableClass, equalTo(exactMessage));
  }

  public static <T extends Runnable, U extends Throwable> ThrowsException<T> throwsException(Class<U> throwableClass, Matcher<String> messageMatcher) {
    return new ThrowsException<>(new IsInstanceOf(throwableClass), messageMatcher);
  }

  public static <T extends Runnable> ThrowsException<T> throwsExceptionWithMessage(String exactMessage) {
    return throwsException(Throwable.class, equalTo(exactMessage));
  }

  public static <T extends Runnable> ThrowsException<T> throwsExceptionWithMessage(Matcher<String> messageMatcher) {
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
