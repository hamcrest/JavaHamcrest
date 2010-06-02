package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import java.util.ArrayList;

public class CombinableMatcher<T> extends BaseMatcher<T> {
    private final Matcher<? super T> matcher;

    public CombinableMatcher(Matcher<? super T> matcher) {
        this.matcher = matcher;
    }

    public boolean matches(Object item) {
        return matcher.matches(item);
    }

    public void describeTo(Description description) {
        description.appendDescriptionOf(matcher);
    }

    public CombinableMatcher<T> and(Matcher<? super T> other) {
        return new CombinableMatcher<T>(new AllOf<T>(templatedListWith(other)));
    }

    public CombinableMatcher<T> or(Matcher<? super T> other) {
        return new CombinableMatcher<T>(new AnyOf<T>(templatedListWith(other)));
    }

    private ArrayList<Matcher<? super T>> templatedListWith(Matcher<? super T> other) {
        ArrayList<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(matcher);
        matchers.add(other);
        return matchers;
    }

    /**
     * This is useful for fluently combining matchers that must both pass.  For example:
     * <pre>
     *   assertThat(string, both(containsString("a")).and(containsString("b")));
     * </pre>
     */
    @Factory
    public static <LHS> CombinableMatcher<LHS> both(Matcher<? super LHS> matcher) {
        return new CombinableMatcher<LHS>(matcher);
    }

    /**
     * This is useful for fluently combining matchers where either may pass, for example:
     * <pre>
     *   assertThat(string, both(containsString("a")).and(containsString("b")));
     * </pre>
     */
    @Factory
    public static <LHS> CombinableMatcher<LHS> either(Matcher<? super LHS> matcher) {
        return new CombinableMatcher<LHS>(matcher);
    }
}