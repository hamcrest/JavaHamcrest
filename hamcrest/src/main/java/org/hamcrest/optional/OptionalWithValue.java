package org.hamcrest.optional;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsAnything;

import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matcher for {@link Optional} that expects that value is present.
 *
 * @param <T> type of {@link Optional} value
 */
public class OptionalWithValue<T> extends TypeSafeDiagnosingMatcher<Optional<T>> {

    private final Matcher<? super T> matcher;

    /**
     * Constructor.
     *
     * @param matcher matcher to validate present optional value
     */
    public OptionalWithValue(Matcher<? super T> matcher) {
        this.matcher = matcher;
    }

    /**
     * Matcher for {@link Optional} that expects that value is present.
     *
     * @param <T> type of optional value
     * @return The matcher.
     */
    public static <T> Matcher<Optional<T>> optionalWithValue() {
        return new OptionalWithValue<>(IsAnything.anything("any"));
    }

    /**
     * Matcher for {@link Optional} that expects that value is present and is equal to <code>value</code>
     *
     * @param <T> type of optional value
     * @param value to validate present optional value
     * @return The matcher.
     */
    public static <T> Matcher<Optional<T>> optionalWithValue(T value) {
        return new OptionalWithValue<>(equalTo(value));
    }

    /**
     * Matcher for {@link Optional} that expects that value is present and matches <code>matcher</code>
     *
     * @param <T> type of optional value
     * @param matcher matcher to validate present optional value
     * @return The matcher.
     */
    public static <T> Matcher<Optional<T>> optionalWithValue(Matcher<? super T> matcher) {
        return new OptionalWithValue<>(matcher);
    }

    @Override
    protected boolean matchesSafely(Optional<T> value, Description mismatchDescription) {
        mismatchDescription.appendText("is " + value);
        return value.isPresent() && matcher.matches(value.get());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("present and matches ")
                .appendDescriptionOf(matcher);
    }
}
