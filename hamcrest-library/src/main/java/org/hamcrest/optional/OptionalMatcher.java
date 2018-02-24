package org.hamcrest.optional;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Optional;

public abstract class OptionalMatcher<T> extends TypeSafeMatcher<Optional<T>> {

    protected abstract String getDescribeText();
    protected abstract String getDescribeMismatchText(Optional<T> item);

    @Override
    public final void describeTo(Description description) {
        description.appendText("Optional<")
                    .appendText(getDescribeText())
                    .appendText(">");
    }


    @Override
    protected final void describeMismatchSafely(Optional<T> item, Description mismatchDescription) {
        mismatchDescription.appendText("was ")
                    .appendText("Optional<")
                    .appendText(getDescribeMismatchText(item))
                    .appendText(">");
    }

    protected final String getTextOrDefault(Optional<T> value, String defaultValue) {
        return value.map(Object::toString).orElse(defaultValue);
    }

}
