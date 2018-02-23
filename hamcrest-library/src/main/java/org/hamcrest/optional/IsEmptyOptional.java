package org.hamcrest.optional;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Optional;

public class IsEmptyOptional<T> extends TypeSafeMatcher<Optional<? extends T>> {


    @Override
    public void describeTo(Description description) {
        description.appendText("Empty Optional");
    }

    @Override
    protected boolean matchesSafely(Optional<? extends T> item) {
        return isNotPresent(item);
    }

    private boolean isNotPresent(Optional<? extends T> actual) {
        return !actual.isPresent();
    }

    public static <T> Matcher<Optional<? extends T>> emptyOptional() {
        return new IsEmptyOptional<>();
    }

}
