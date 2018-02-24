package org.hamcrest.optional;

import org.hamcrest.Matcher;

import java.util.Optional;

public class HasValueOptional<T> extends OptionalMatcher<T> {


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
    protected String getDescribeText() {
        return getTextOrDefault(optionalValue, "Some Value");
    }

    @Override
    protected String getDescribeMismatchText(Optional<T> item) {
        return getTextOrDefault(item, "Empty");
    }


    public static <T> Matcher<Optional<T>> optionalWithValue(){
        return new HasValueOptional<>(null);
    }

    public static <T> Matcher<Optional<T>> optionalWithValue(T value){
        if (value == null){
            throw new IllegalArgumentException("Value can't be null. Use IsEmptyOptional.emptyOptional instead.");
        }

        return new HasValueOptional<>(value);
    }
}
