package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Decorates another Matcher, retaining the behaviour but allowing tests
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

    @Override
    public boolean matches(T arg) {
        return matcher.matches(arg);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is ").appendDescriptionOf(matcher);
    }

    @Override
    public void describeMismatch(T item, Description mismatchDescription) {
        matcher.describeMismatch(item, mismatchDescription);
    }

    /**
     * Decorates another Matcher, retaining its behaviour, but allowing tests
     * to be slightly more expressive.
     * For example:
     * <pre>assertThat(cheese, is(equalTo(smelly)))</pre>
     * instead of:
     * <pre>assertThat(cheese, equalTo(smelly))</pre>
     * 
     */
    public static <T> Matcher<T> is(Matcher<T> matcher) {
        return new Is<>(matcher);
    }

    /**
     * A shortcut to the frequently used <code>is(equalTo(x))</code>.
     * For example:
     * <pre>assertThat(cheese, is(smelly))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(equalTo(smelly)))</pre>
     * 
     */
    public static <T> Matcher<T> is(T value) {
        return is(equalTo(value));
    }

    /**
     * A shortcut to the frequently used <code>is(instanceOf(SomeClass.class))</code>.
     * For example:
     * <pre>assertThat(cheese, isA(Cheddar.class))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(instanceOf(Cheddar.class)))</pre>
     * 
     */
    public static <T> Matcher<T> isA(Class<?> type) {
        return is(IsInstanceOf.<T>instanceOf(type));
    }
}
