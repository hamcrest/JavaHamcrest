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

    private final String description;

    public IsAnything() {
        this("ANYTHING");
    }

    public IsAnything(String description) {
        this.description = description;
    }

    public boolean matches(Object o) {
        return true;
    }

    public void describeTo(Description description) {
        description.appendText(this.description);
    }

    @Factory
    public static <T> Matcher<T> anything() {
        return new IsAnything<T>();
    }

    @Factory
    public static <T> Matcher<T> anything(String description) {
        return new IsAnything<T>(description);
    }
}
