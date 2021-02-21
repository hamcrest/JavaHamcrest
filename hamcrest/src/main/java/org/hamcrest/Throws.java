package org.hamcrest;

import static java.util.Objects.requireNonNull;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * @author Peter De Maeyer
 */
public final class Throws<T extends Throwable> implements SelfDescribing {

    private final Matcher<? super T> matcher;

    public Throws(Matcher<? super T> throwableMatcher) {
        requireNonNull(throwableMatcher);
        this.matcher = new BaseMatcher<T>() {

            @Override
            public boolean matches(Object actual) {
                return throwableMatcher.matches(actual);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("throws ");
                throwableMatcher.describeTo(description);
            }

            @Override
            public void describeMismatch(Object item, Description mismatchDescription) {
                mismatchDescription.appendText("threw but ");
                throwableMatcher.describeMismatch(item, mismatchDescription);
            }
        };
    }

    Matcher<? super T> asMatcher() {
        return matcher;
    }

    @Override
    public void describeTo(Description description) {
        matcher.describeTo(description);
    }

    public void describeMismatch(Description description) {
        description.appendText("did not throw");
    }

    public static <T extends Throwable> Matcher<T> withMessage(Matcher<String> messageMatcher) {
        return new TypeSafeMatcher<T>() {

            @Override
            protected boolean matchesSafely(T throwable) {
                return messageMatcher.matches(throwable.getMessage());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with message ");
                messageMatcher.describeTo(description);
            }

            @Override
            protected void describeMismatchSafely(T item, Description mismatchDescription) {
                mismatchDescription.appendText("message ");
                messageMatcher.describeMismatch(item.getMessage(), mismatchDescription);
            }
        };
    }

    public static <T extends Throwable> Matcher<T> becauseOf(Matcher<? extends Throwable> causeMatcher) {
        return new TypeSafeMatcher<T>() {

            @Override
            protected boolean matchesSafely(T throwable) {
                return causeMatcher.matches(throwable.getCause());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("because of ");
                causeMatcher.describeTo(description);
            }

            @Override
            protected void describeMismatchSafely(T item, Description mismatchDescription) {
                mismatchDescription.appendText("cause ");
                causeMatcher.describeMismatch(item.getCause(), mismatchDescription);
            }
        };
    }

    public static <T extends Throwable> Throws<T> doesThrow(Matcher<? super T> throwableMatcher) {
        return new Throws<T>(throwableMatcher);
    }

    public static <T extends Throwable> Throws<T> throwsInstanceOf(Class<T> throwableType) {
        return doesThrow(instanceOf(throwableType));
    }
}
