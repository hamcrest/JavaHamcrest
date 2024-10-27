package org.hamcrest.exception;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher that checks if a Runnable throws an expected exception when run.
 *
 * @param <T> the type of the expected exception.
 */
public class ThrowsExceptionEqualTo<T extends Throwable> extends TypeSafeDiagnosingMatcher<T> {
    private final T expectedException;

    /**
     * Constructor, best called from one of the static factory methods.
     *
     * @param expectedException the expected exception.
     */
    public ThrowsExceptionEqualTo(T expectedException) {
        this.expectedException = expectedException;
    }

    public static <U extends Throwable> ThrowsExceptionEqualTo<U> exceptionEqualTo(U expectedException) {
        return new ThrowsExceptionEqualTo<>(expectedException);
    }

    @Override
    public boolean matchesSafely(T item, Description mismatchDescription) {
        if (!this.expectedException.getClass().isAssignableFrom(item.getClass())) {
            mismatchDescription.appendValue(item.getClass().getName()).appendText(" instead of a ").appendValue(this.expectedException.getClass().getName()).appendText(" exception");
            return false;
        }
        if (!item.getMessage().equals(this.expectedException.getMessage())) {
            mismatchDescription.appendValue(item.getClass().getName()).appendText(" with message ").appendValue(item.getMessage()).appendText(" instead of ").appendValue(this.expectedException.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expectedException.getClass().getName()).appendText(" with message ").appendValue(expectedException.getMessage());
    }
}