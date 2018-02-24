package org.hamcrest.optional;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Optional;

public class IsEmptyOptional<T> extends TypeSafeMatcher<Optional<T>> {


    @Override
    public void describeTo(Description description) {
        description
                .appendText("Optional<Empty>");
    }

    @Override
    protected void describeMismatchSafely(Optional<T> item, Description mismatchDescription) {
        mismatchDescription
                .appendText("was ")
                    .appendText("Optional<" + item.map(Object::toString).orElse("Empty") + ">");

    }

    @Override
    protected boolean matchesSafely(Optional<T> item) {
        return isNotPresent(item);
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
