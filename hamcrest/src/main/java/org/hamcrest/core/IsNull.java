package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static org.hamcrest.core.IsNot.not;

/**
 * Is the value null?
 */
public class IsNull<T> extends BaseMatcher<T> {
    @Override
    public boolean matches(Object o) {
        return o == null;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("null");
    }

    /**
     * Creates a matcher that matches if examined object is <code>null</code>.
     * For example:
     * <pre>assertThat(cheese, is(nullValue())</pre>
     * 
     */
    public static <T> Matcher<T> nullValue() {
        return new IsNull<>();
    }

    /**
     * A shortcut to the frequently used <code>not(nullValue())</code>.
     * For example:
     * <pre>assertThat(cheese, is(notNullValue()))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(not(nullValue())))</pre>
     * 
     */
    public static <T> Matcher<T> notNullValue() {
        return Matchers.<T>not(IsNull.<T>nullValue());
    }

    /**
     * Creates a matcher that matches if examined object is <code>null</code>. Accepts a
     * single dummy argument to facilitate type inference.
     * For example:
     * <pre>assertThat(cheese, is(nullValue(Cheese.class))</pre>
     * 
     * @param type
     *     dummy parameter used to infer the generic type of the returned matcher
     */
    public static <T> Matcher<T> nullValue(Class<T> type) {
        return new IsNull<T>();
    }

    /**
     * A shortcut to the frequently used <code>not(nullValue(X.class)). Accepts a
     * single dummy argument to facilitate type inference.</code>.
     * For example:
     * <pre>assertThat(cheese, is(notNullValue(X.class)))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(not(nullValue(X.class))))</pre>
     * 
     * @param type
     *     dummy parameter used to infer the generic type of the returned matcher
     *  
     */
    public static <T> Matcher<T> notNullValue(Class<T> type) {
        return not(nullValue(type));
    }
}

