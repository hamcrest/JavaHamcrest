/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Matcher;

/**
 * Always evaluate to true or false (depending on what's passed into the constructor).
 */
public class Always implements Matcher {

    private final boolean matches;
    private final String description;

    public Always(boolean matches) {
        this(matches, "always evaluates to " + matches);
    }

    public Always(boolean matches, String description) {
        this.matches = matches;
        this.description = description;
    }

    public boolean match(Object o) {
        return matches;
    }

    public void describeTo(StringBuffer buffer) {
        buffer.append(description);
    }

}
