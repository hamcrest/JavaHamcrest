package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class IsCompatibleType implements Matcher<Class> {
    private final Class type;

    public IsCompatibleType(Class type) {
        this.type = type;
    }

    public boolean match(Class cls) {
        return type.isAssignableFrom(cls);
    }

    public void describeTo(Description description) {
        description.appendText("type < ").appendText(type.getName());
    }
}
