package org.hamcrest.optional;

import org.hamcrest.Matcher;

import java.util.Optional;

public class IsEmptyOptional<T> extends OptionalMatcher<T> {



    @Override
    protected boolean matchesSafely(Optional<T> item) {
        return isNotPresent(item);
    }

    @Override
    protected String getDescribeText() {
        return "Empty";
    }

    @Override
    protected String getDescribeMismatchText(Optional<T> item) {
        return getTextOrDefault(item, "Empty");
    }

    private boolean isNotPresent(Optional<T> actual) {
        return !actual.isPresent();
    }

    /**
     * Creates a matcher for {@link Optional}s matching when the examined optional has no value.
     * For example:
     * <pre>assertThat(Optional.empty(), emptyOptional())</pre>
     *
     */
    public static <T> Matcher<Optional<T>> emptyOptional() {
        return new IsEmptyOptional<>();
    }

}
