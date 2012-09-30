package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Decorates another Matcher, retaining the behaviour but allowing tests
 * to be slightly more expressive.
 *
 * For example:  assertThat(lines, equalTo(asList("foo", "bar")))
 *          vs.  assertThat(lines, are(equalTo(asList("foo", "bar"))))
 */
public class Are<T> extends BaseMatcher<T> {
    private final Matcher<T> matcher;

    public Are(Matcher<T> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Object arg) {
        return matcher.matches(arg);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("are ").appendDescriptionOf(matcher);
    }

    @Override
    public void describeMismatch(Object item, Description mismatchDescription) {
        matcher.describeMismatch(item, mismatchDescription);
    }

    /**
     * Decorates another Matcher, retaining its behaviour, but allowing tests
     * to be slightly more expressive.
     * <p/>
     * For example:
     * <pre>assertThat(lines, are(equalTo(asList("foo", "bar"))))</pre>
     * instead of:
     * <pre>assertThat(lines, equalTo(asList("foo", "bar")))</pre>
     *
     */
    @Factory
    public static <T> Matcher<T> are(Matcher<T> matcher) {
        return new Are<T>(matcher);
    }

    /**
     * A shortcut to the frequently used <code>are(equalTo(x))</code>.
     * <p/>
     * For example:
     * <pre>assertThat(lines, are(asList("foo", "bar")))</pre>
     * instead of:
     * <pre>assertThat(lines, are(equalTo(asList("foo", "bar"))))</pre>
     *
     */
    @Factory
    public static <T> Matcher<T> are(T value) {
        return are(equalTo(value));
    }
}
