/*  Copyright (c) 2000-2006 hamcrest.org
 */

package org.hamcrest.core;


import org.hamcrest.Description;
import org.hamcrest.Function;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * The extract matcher main purpose is to match against properties of an object.
 * Is a second-order matcher, which takes as argument a function which retrieves
 * the value to be matched from the given object.
 *
 * @see org.hamcrest.Function
 */
public class ExtractMatcher<T, V> extends TypeSafeMatcher<T> {
    public static <T, V> ExtractMatcher<T, V> valueOf (Function<T, V> extractFunction, Matcher<V> innerMatcher) {
        return new ExtractMatcher<T, V>(extractFunction, innerMatcher);
    }

    private Function<T, V> function;
    private Matcher<V> matcher;

    public ExtractMatcher(Function<T, V> function, Matcher<V> matcher) {
        this.function = function;
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(T item) {
        return matcher.matches(function.apply(item));
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(function).appendText(" ").appendDescriptionOf(matcher);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        matcher.describeMismatch(function.apply(item), mismatchDescription.appendDescriptionOf(function).appendText(" "));
    }
}
