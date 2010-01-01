/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.BaseMatcher;


/**
 * A matcher that always returns <code>true</code>.
 */
public class IsAnything<T> extends BaseMatcher<T> {

    private final String message;

    public IsAnything() {
        this("ANYTHING");
    }

    public IsAnything(String message) {
        this.message = message;
    }

    public boolean matches(Object o) {
        return true;
    }

    public void describeTo(Description description) {
        description.appendText(message);
    }

    /**
     * This matcher always evaluates to true.
     */
    @Factory
    public static Matcher<Object> anything() {
        return new IsAnything<Object>();
    }

    /**
     * This matcher always evaluates to true.
     *
     * @param description A meaningful string used when describing itself.
     */
    @Factory
    public static Matcher<Object> anything(String description) {
        return new IsAnything<Object>(description);
    }
}
