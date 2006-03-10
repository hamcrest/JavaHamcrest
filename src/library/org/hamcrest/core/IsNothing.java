package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;


public class IsNothing implements Matcher {
    public static final IsNothing INSTANCE = new IsNothing();
    private final String description;

    public IsNothing() {
        this("NOTHING");
    }

    public IsNothing(String description) {
        this.description = description;
    }

    public boolean match(Object o) {
        return false;
    }

    public void describeTo(Description description) {
        description.appendText(this.description);
    }
}
