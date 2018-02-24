package org.hamcrest.optional;

import org.hamcrest.Matcher;

import java.util.Optional;

public class HasValueOptional<T> extends OptionalMatcher<T> {


    private final Optional<T> optionalValue;

    private HasValueOptional(T value) {
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


    /**
     * Creates a matcher for {@link Optional}s matching when the examined optional has some value.
     * For example:
     * <pre>assertThat(myOptional, is(optionalWithValue()))</pre>
     */
    public static <T> Matcher<Optional<T>> optionalWithValue(){
        return new HasValueOptional<>(null);
    }

    /**
     * Creates a matcher for {@link Optional}s matching when the examined optional has specific value.
     * For example:
     * <pre>assertThat(myOptional, is(optionalWithValue(2)))</pre>
     *
     * @param value
     *       value that the examined object must have
     */
    public static <T> Matcher<Optional<T>> optionalWithValue(T value){
        if (value == null){
            throw new IllegalArgumentException("Value can't be null. Use IsEmptyOptional.emptyOptional instead.");
        }

        return new HasValueOptional<>(value);
    }
}
