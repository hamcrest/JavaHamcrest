package org.hamcrest.exception;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.exception.ThrowsExceptionEqualTo.exceptionEqualTo;

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
    ThrowsException(Matcher<? super Throwable> elementMatcher) {
        this.exceptionMatcher = elementMatcher;
    }

    public static <U extends Runnable, T extends Throwable> ThrowsException<U> throwsException(T item) {
        return new ThrowsException<>(exceptionEqualTo(item));
    }

    public static <U extends Runnable> ThrowsException<U> throwsException(Matcher<? super Throwable> exceptionMatcher) {
        return new ThrowsException<>(exceptionMatcher);
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
