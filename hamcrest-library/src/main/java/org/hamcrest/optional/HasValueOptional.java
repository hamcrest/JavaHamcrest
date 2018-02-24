package org.hamcrest.optional;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Optional;

public class HasValueOptional<T> extends TypeSafeMatcher<Optional<T>> {


    private final Optional<T> optionalValue;

    public HasValueOptional(T value) {
        optionalValue = Optional.ofNullable(value);
    }

    @Override
    protected boolean matchesSafely(Optional<T> item) {

        if (optionalValue.isPresent()){
            return item.equals(optionalValue);
        }

        return item.isPresent();
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("Optional<" + optionalValue.map(Object::toString).orElse("Some Value") + ">");
    }

    @Override
    protected void describeMismatchSafely(Optional<T> item, Description mismatchDescription) {

        mismatchDescription
                .appendText("was ")
                    .appendText("Optional<" + item.map(Object::toString).orElse("Empty") + ">");
    }


    public static <T> Matcher<Optional<T>> hasValue(){
        return new HasValueOptional<>(null);
    }

    public static <T> Matcher<Optional<T>> hasValue(T value){
        if (value == null){
            throw new IllegalArgumentException("Value can't be null. Use IsEmptyOptional.emptyOptional instead.");
        }

        return new HasValueOptional<>(value);
    }
}
