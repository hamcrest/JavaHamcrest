package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class IsCompatibleType implements Matcher {
    private final Class type;

    public IsCompatibleType(Class type) {
        this.type = type;
    }

    public boolean match(Object o) {
        return o instanceof Class && type.isAssignableFrom((Class) o);
    }

    public void describeTo(Description description) {
        description.appendText("type < ").appendText(type.getName());
    }
}
