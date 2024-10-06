package org.hamcrest.exception;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher that checks if a Runnable throws an expected exception when run.
 *
 * @param <T> the type of the expected exception.
 */
public class ThrowsException<T extends Throwable> extends TypeSafeMatcher<Runnable> {
    private final T expectedException;
    private Throwable actualThrowable;

    /**
     * Constructor
     *
     * @param expectedException the expected exception.
     */
    ThrowsException(T expectedException) {
        super(Runnable.class);
        this.expectedException = expectedException;
    }

    public static <U extends Throwable> ThrowsException<U> throwsException(U expectedException) {
        return new ThrowsException<>(expectedException);
    }

    @Override
    protected boolean matchesSafely(Runnable item) {
        try {
            item.run();
            return false;
        } catch (Throwable t) {
            this.actualThrowable = t;
            return expectedException.getClass().isInstance(t) && t.getMessage().equals(expectedException.getMessage());
        }
    }

    @Override
    protected void describeMismatchSafely(Runnable item, Description mismatchDescription) {
        if (expectedException.getClass().isInstance(actualThrowable)) {
            mismatchDescription.appendText("threw ").appendValue(actualThrowable.getClass().getName()).appendText(" with message ").appendValue(actualThrowable.getMessage()).appendText(" instead of ").appendValue(expectedException.getMessage());
            return;
        }
        mismatchDescription.appendText("threw a ").appendValue(actualThrowable.getClass().getName()).appendText(" instead of a ").appendValue(expectedException.getClass().getName()).appendText(" exception");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("throws ").appendValue(expectedException.getClass().getName()).appendText(" with message ").appendValue(expectedException.getMessage());
    }
}
