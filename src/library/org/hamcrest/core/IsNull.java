/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.core.IsNot.not;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.BaseMatcher;

/**
 * Is the value null?
 */
public class IsNull<T> extends BaseMatcher<T> {
    public boolean matches(Object o) {
        return o == null;
    }

    public void describeTo(Description description) {
        description.appendText("null");
    }

    @Factory
    public static <T> Matcher<T> isNull() {
        return new IsNull<T>();
    }

    @Factory
    public static <T> Matcher<T> isNotNull() {
        return not(IsNull.<T>isNull());
    }
}

