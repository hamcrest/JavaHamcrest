package org.hamcrest.exception;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.exception.ThrowsExceptionEqual.throwsExceptionEqual;

/**
 * Tests if a Runnable throws a matching exception.
 */
public class ThrowsException extends TypeSafeMatcher<Runnable> {

    private final Matcher<Runnable> elementMatcher;

    /**
     * Constructor, best called from one of the static factory methods.
     * @param elementMatcher matches the expected element
     */
    ThrowsException(Matcher<Runnable> elementMatcher) {
        super(Runnable.class);
        this.elementMatcher = elementMatcher;
    }

    public static <U extends Throwable> ThrowsException throwsException(U item) {
        return new ThrowsException(throwsExceptionEqual(item));
    }

    public static ThrowsException throwsException(Matcher<Runnable> itemMatcher) {
        return new ThrowsException(itemMatcher);
    }

    @Override
    protected boolean matchesSafely(Runnable item) {
        return elementMatcher.matches(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a runnable throwing ").appendDescriptionOf(elementMatcher);
    }

    @Override
    protected void describeMismatchSafely(Runnable item, Description mismatchDescription) {
        elementMatcher.describeMismatch(item, mismatchDescription);
    }
}
