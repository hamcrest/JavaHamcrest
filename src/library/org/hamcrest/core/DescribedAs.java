/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Provides a custom description to another matcher.
 */
public class DescribedAs<T> implements Matcher<T> {

    private final String description;
    private final Matcher<T> matcher;

    public DescribedAs(String description, Matcher<T> matcher) {
        this.description = description;
        this.matcher = matcher;
    }

    public boolean match(T o) {
        return matcher.match(o);
    }

    public void describeTo(Description description) {
        description.appendText(this.description);
    }
}
