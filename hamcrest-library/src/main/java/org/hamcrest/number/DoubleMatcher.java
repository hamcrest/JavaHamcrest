package org.hamcrest.number;

import org.hamcrest.BaseMatcher;

public abstract class DoubleMatcher extends BaseMatcher<Double> {
    public boolean matches(Object item) {
        return item instanceof Double && matchesSafely((Double)item);
    }
    
    protected abstract boolean matchesSafely(Double d);
}
