package org.hamcrest.object;

import org.hamcrest.Matcher;
import org.hamcrest.Description;

public class HasToString implements Matcher {
    private final Matcher toStringMatcher;

    public HasToString(Matcher toStringMatcher) {
        this.toStringMatcher = toStringMatcher;
    }

    public boolean match(Object o) {
        return toStringMatcher.match(o.toString());
    }

    public void describeTo(Description description) {
        description.append("toString(");
        toStringMatcher.describeTo(description);
        description.append(")");
    }
}
