package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.hamcrest.Description;

public class IsCompatibleType implements Matcher {
    private final Class type;

    public IsCompatibleType(Class type) {
        this.type = type;
    }

    public boolean match(Object o) {
        return o instanceof Class && type.isAssignableFrom((Class) o);
    }

    public void describeTo(Description description) {
        description.append("type < ").append(type.getName());
    }
}
