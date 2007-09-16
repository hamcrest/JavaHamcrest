package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.introspection.Combination;

abstract class ShortcutCombination<T> extends BaseMatcher<T> implements Combination {
    private final Iterable<Matcher<? super T>> matchers;

    public ShortcutCombination(Iterable<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    public boolean matches(Object o) {
        for (Matcher<? super T> matcher : matchers) {
            if (matcher.matches(o) == shortcut()) {
                return shortcut();
            }
        }
        return !shortcut();
    }
    
    protected abstract boolean shortcut();
    
    public void describeTo(Description description) {
        description.appendList("(", " and ", ")", matchers);
    }
    
    public Iterable<? extends Matcher<? super T>> combined() {
        return matchers;
    }
}
