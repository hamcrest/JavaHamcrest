/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.hamcrest.Description;

/**
 * Provides a custom description to another matcher.
 */
public class DescribedAs implements Matcher {

    private final String description;
    private final Matcher matcher;

    public DescribedAs(String description, Matcher matcher) {
        this.description = description;
        this.matcher = matcher;
    }

    public boolean match(Object o) {
        return matcher.match(o);
    }

    public void describeTo(Description description) {
        description.append(this.description);
    }
}
