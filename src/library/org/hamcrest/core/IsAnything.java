/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.hamcrest.Description;


/**
 * A matcher that always returns <code>true</code>.
 */
public class IsAnything implements Matcher {
    public static final IsAnything INSTANCE = new IsAnything();
    private final String description;

    public IsAnything() {
        this("ANYTHING");
    }

    public IsAnything(String description) {
        this.description = description;
    }

    public boolean match(Object o) {
        return true;
    }

    public void describeTo(Description description) {
        description.append(this.description);
    }
}
