package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;

public class IsCompatibleType<T extends Class<T>> implements Matcher<Class<T>> {
    private final Class<? extends T> type;

    public IsCompatibleType(Class<? extends T> type) {
        this.type = type;
    }

    public boolean match(Class cls) {
        return type.isAssignableFrom(cls);
    }

    public void describeTo(Description description) {
        description.appendText("type < ").appendText(type.getName());
    }

    @Factory
    public static <T> Matcher<Class<? extends T>> compatibleType(Class<? extends T> baseType) {
        return new IsCompatibleType(baseType);
    }

}
