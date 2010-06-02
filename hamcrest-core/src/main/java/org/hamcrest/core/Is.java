package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Decorates another Matcher, retaining the behavior but allowing tests
 * to be slightly more expressive.
 *
 * For example:  assertThat(cheese, equalTo(smelly))
 *          vs.  assertThat(cheese, is(equalTo(smelly)))
 */
public class Is<T> extends BaseMatcher<T> {
    private final Matcher<T> matcher;

    public Is(Matcher<T> matcher) {
        this.matcher = matcher;
    }

    public boolean matches(Object arg) {
        return matcher.matches(arg);
    }

    public void describeTo(Description description) {
        description.appendText("is ").appendDescriptionOf(matcher);
    }

    @Override
    public void describeMismatch(Object item, Description mismatchDescription) {
    	// TODO(ngd): unit tests....
    	matcher.describeMismatch(item, mismatchDescription);
    }

    /**
     * Decorates another Matcher, retaining the behavior but allowing tests
     * to be slightly more expressive.
     *
     * For example:  assertThat(cheese, equalTo(smelly))
     *          vs.  assertThat(cheese, is(equalTo(smelly)))
     */
    @Factory
    public static <T> Matcher<T> is(Matcher<T> matcher) {
        return new Is<T>(matcher);
    }

    /**
     * This is a shortcut to the frequently used is(equalTo(x)).
     *
     * For example:  assertThat(cheese, is(equalTo(smelly)))
     *          vs.  assertThat(cheese, is(smelly))
     */
    @Factory
    public static <T> Matcher<T> is(T value) {
        return is(equalTo(value));
    }

    /**
     * This is a shortcut to the frequently used is(instanceOf(SomeClass.class)).
     *
     * For example:  assertThat(cheese, is(instanceOf(Cheddar.class)))
     *          vs.  assertThat(cheese, is(Cheddar.class))
     *
     * @deprecated use isA(Class<T> type) instead.
     */
    @Factory
    @Deprecated
    public static <T> Matcher<T> is(Class<T> type) {
        final Matcher<T> typeMatcher = instanceOf(type);
        return is(typeMatcher);
    }

    @Factory
    @Deprecated
    public static <T> Matcher<T> isA(Class<T> type) {
        final Matcher<T> typeMatcher = instanceOf(type);
        return is(typeMatcher);
    }
}
