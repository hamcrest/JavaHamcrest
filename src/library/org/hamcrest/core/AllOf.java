package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;

import java.util.Arrays;

/**
 * Calculates the logical conjunction of two matchers. Evaluation is
 * shortcut, so that the second matcher is not called if the first
 * matcher returns <code>false</code>.
 */
public class AllOf<T> extends BaseMatcher<T> {
    private final Iterable<Matcher<T>> matchers;

    public AllOf(Iterable<Matcher<T>> matchers) {
        this.matchers = matchers;
    }

    public boolean matches(Object o) {
        for (Matcher<T> matcher : matchers) {
            if (!matcher.matches(o)) {
                return false;
            }
        }
        return true;
    }

    public void describeTo(Description description) {
        description.appendText("(");
        boolean seenFirst = false;
        for (Matcher<T> matcher : matchers) {
            if (seenFirst) {
                description.appendText(" and ");
            } else {
                seenFirst = true;
            }
            matcher.describeTo(description);
        }
        description.appendText(")");
    }

    @Factory
    public static <T> Matcher<T> allOf(Matcher<T>... matchers) {
        return allOf(Arrays.asList(matchers));
    }

    @Factory
    public static <T> Matcher<T> allOf(Iterable<Matcher<T>> matchers) {
        return new AllOf<T>(matchers);
    }

}
