package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;

public class IsCompatibleType<T> implements Matcher<Class<?>> {
    private final Class<T> type;
    
    public IsCompatibleType(Class<T> type) {
        this.type = type;
    }
    
    public boolean match(Class<?> cls) {
        return type.isAssignableFrom(cls);
    }
    
    public void describeTo(Description description) {
        description.appendText("type < ").appendText(type.getName());
    }
    
    @Factory
    public static <T> Matcher<Class<?>> compatibleType(Class<T> baseType) {
        return new IsCompatibleType<T>(baseType);
    }
}
