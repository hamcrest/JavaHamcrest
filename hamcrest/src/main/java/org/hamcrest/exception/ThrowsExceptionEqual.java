package org.hamcrest.exception;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher that checks if a Runnable throws an expected exception when run.
 *
 * @param <T> the type of the expected exception.
 */
public class ThrowsExceptionEqual<T extends Throwable> extends TypeSafeMatcher<Runnable> {
    private final T item;
    private Throwable actualItem;

    /**
     * Constructor
     *
     * @param item the expected exception.
     */
    ThrowsExceptionEqual(T item) {
        super(Runnable.class);
        this.item = item;
    }

    public static <U extends Throwable> ThrowsExceptionEqual<U> throwsExceptionEqual(U item) {
        return new ThrowsExceptionEqual<>(item);
    }

    @Override
    protected boolean matchesSafely(Runnable item) {
        try {
            item.run();
            return false;
        } catch (Throwable t) {
            this.actualItem = t;
            return this.item.getClass().isAssignableFrom(t.getClass()) && t.getMessage().equals(this.item.getMessage());
        }
    }

    @Override
    protected void describeMismatchSafely(Runnable item, Description mismatchDescription) {
        if (this.item.getClass().isInstance(actualItem)) {
            mismatchDescription.appendText("threw ").appendValue(actualItem.getClass().getName()).appendText(" with message ").appendValue(actualItem.getMessage()).appendText(" instead of ").appendValue(this.item.getMessage());
            return;
        }
        mismatchDescription.appendText("threw a ").appendValue(actualItem.getClass().getName()).appendText(" instead of a ").appendValue(this.item.getClass().getName()).appendText(" exception");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("throws ").appendValue(item.getClass().getName()).appendText(" with message ").appendValue(item.getMessage());
    }
}
