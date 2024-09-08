package org.hamcrest.optional;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Optional;

/**
 * Matcher that expects an empty {@link Optional}.

 * @param <T> type of {@link Optional} value
 */
public class OptionalEmpty<T> extends TypeSafeDiagnosingMatcher<Optional<T>> {

    /**
     * Constructor, best called from {@link #emptyOptional()}.
     */
    public OptionalEmpty() {
    }

    /**
     * Matcher that expects empty {@link Optional}.
     *
     * @param <T> type of optional value
     * @return The matcher.
     */
    public static <T> Matcher<Optional<T>> emptyOptional() {
        return new OptionalEmpty<>();
    }

    @Override
    protected boolean matchesSafely(Optional<T> value, Description mismatchDescription) {
        mismatchDescription.appendText("is " + value);
        return !value.isPresent();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("empty");
    }
}
