package org.hamcrest.optional;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Optional;

public class IsEmptyOptional<T> extends TypeSafeMatcher<Optional<? extends T>> {


    @Override
    public void describeTo(Description description) {
        description.appendValue(Optional.empty());
    }

    @Override
    protected boolean matchesSafely(Optional<? extends T> item) {
        return isNotPresent(item);
    }

    private boolean isNotPresent(Optional<? extends T> actual) {
        return !actual.isPresent();
    }

    /**
     * Creates a matcher for {@link Optional}s matching when the examined optional has no value.
     * For example:
     * <pre>assertThat(Optional.empty(), emptyOptional())</pre>
     *
     */
    public static <T> Matcher<Optional<? extends T>> emptyOptional() {
        return new IsEmptyOptional<>();
    }

}
