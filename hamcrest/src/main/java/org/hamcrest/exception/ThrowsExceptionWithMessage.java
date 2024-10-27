package org.hamcrest.exception;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * A matcher that checks if a Runnable throws an expected exception when run.
 *
 * @param <T> the type of the expected exception.
 */
public class ThrowsExceptionWithMessage<T extends Throwable> extends TypeSafeDiagnosingMatcher<T> {
    private final Matcher<? super String> messageMatcher;

    /**
     * Constructor, best called from one of the static factory methods.
     *
     * @param messageMatcher matches the exception message
     */
    public ThrowsExceptionWithMessage(Matcher<? super String> messageMatcher) {
        this.messageMatcher = messageMatcher;
    }

    public static <U extends Throwable, V extends String> ThrowsExceptionWithMessage<U> withMessage(V message) {
        return new ThrowsExceptionWithMessage<>(equalTo(message));
    }

    public static <U extends Throwable> ThrowsExceptionWithMessage<U> withMessage(Matcher<? super String> messageMatcher) {
        return new ThrowsExceptionWithMessage<>(messageMatcher);
    }

    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription) {
        if (!this.messageMatcher.matches(item.getMessage())) {
            mismatchDescription.appendText("an exception with message ").appendValue(item.getMessage()).appendText(" instead of ").appendDescriptionOf(messageMatcher);
            return false;
        }

        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an exception with message ").appendDescriptionOf(messageMatcher);
    }
}
