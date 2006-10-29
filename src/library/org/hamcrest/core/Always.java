/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;

/**
 * Always passes or failes, ignoring what value is passed to it.
 */
public class Always<T> implements Matcher<T> {

    private final boolean pass;
    private final String description;

    public Always(boolean pass) {
        this(pass, "always " + (pass ? "passes" : "fails"));
    }

    public Always(boolean matches, String description) {
        this.pass = matches;
        this.description = description;
    }

    public boolean match(Object o) {
        return pass;
    }

    public void describeTo(Description description) {
        description.appendText(this.description);
    }

    @Factory
    public static <T> Matcher<T> alwaysPasses() {
        return new Always<T>(true);
    }

    @Factory
    public static <T> Matcher<T> alwaysPasses(String description) {
        return new Always<T>(true, description);
    }

    @Factory
    public static <T> Matcher<T> alwaysFails() {
        return new Always<T>(false);
    }

    @Factory
    public static <T> Matcher<T> alwaysFails(String description) {
        return new Always<T>(false, description);
    }
}
