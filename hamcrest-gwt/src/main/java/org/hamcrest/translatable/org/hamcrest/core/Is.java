package org.hamcrest.core;

import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Decorates another Matcher, retaining the behavior but allowing tests
 * to be slightly more expressive.
 *
 * For example:  assertThat(cheese, equalTo(smelly))
 *          vs.  assertThat(cheese, is(equalTo(smelly)))
 */
//pmuetschard: This is the GWT translatable version, which does not use org.hamcrest.core.IsInstanceOf
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
}
