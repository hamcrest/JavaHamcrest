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

    public static <U extends Runnable> ThrowsException<U> throwsException() {
        return new ThrowsException<>(instanceOf(Throwable.class));
    }

    public static <U extends Runnable, T extends Throwable> ThrowsException<U> throwsException(T item) {
        return new ThrowsException<>(exceptionEqualTo(item));
    }

    public static <U extends Runnable> ThrowsException<U> throwsException(Matcher<? super Throwable> exceptionMatcher) {
        return new ThrowsException<>(exceptionMatcher);
    }

    public static <U extends Runnable, T extends Throwable> ThrowsException<U> throwsException(Class<T> item) {
        return new ThrowsException<>(instanceOf(item));
    }

    public static <U extends Runnable, T extends Throwable> ThrowsException<U> throwsException(Class<T> item , String message) {
        return new ThrowsException<>(allOf(instanceOf(item), withMessage(message)));
    }

    public static <U extends Runnable, T extends Throwable> ThrowsException<U> throwsException(Class<T> item , Matcher<? super Throwable> exceptionMatcher) {
        return new ThrowsException<>(allOf(instanceOf(item), exceptionMatcher));
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
