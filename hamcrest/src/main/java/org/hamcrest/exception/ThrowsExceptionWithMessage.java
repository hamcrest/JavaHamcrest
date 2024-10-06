package org.hamcrest.exception;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * A matcher that checks if a Runnable throws an expected exception when run.
 *
 * @param <T> the type of the expected exception.
 */
public class ThrowsExceptionWithMessage<T extends String> extends TypeSafeMatcher<Runnable> {
    private final Matcher<? super T> messageMatcher;
    private String actualMessage;

    /**
     * Constructor, best called from one of the static factory methods.
     *
     * @param messageMatcher matches the exception message
     */
    ThrowsExceptionWithMessage(Matcher<? super T> messageMatcher) {
        super(Runnable.class);
        this.messageMatcher = messageMatcher;
    }

    public static <U extends String> ThrowsExceptionWithMessage<U> withMessage(U message) {
        return new ThrowsExceptionWithMessage<>(equalTo(message));
    }

    public static <U extends String> ThrowsExceptionWithMessage<U> withMessage(Matcher<? super U> messageMatcher) {
        return new ThrowsExceptionWithMessage<>(messageMatcher);
    }

    @Override
    protected boolean matchesSafely(Runnable item) {
        try {
            item.run();
            return false;
        } catch (Throwable t) {
            actualMessage = t.getMessage();
            return this.messageMatcher.matches(t.getMessage());
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a runnable throwing an exception with message ").appendDescriptionOf(messageMatcher);
    }

    @Override
    protected void describeMismatchSafely(Runnable item, Description mismatchDescription) {
        mismatchDescription.appendText("exception message was ").appendValue(actualMessage).appendText(" instead of ").appendDescriptionOf(messageMatcher);
    }
}
