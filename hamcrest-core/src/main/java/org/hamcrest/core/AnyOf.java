package org.hamcrest.core;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Calculates the logical disjunction of two matchers. Evaluation is
 * shortcut, so that the second matcher is not called if the first
 * matcher returns <code>true</code>.
 */
public class AnyOf<T> extends ShortcutCombination<T> {
    public AnyOf(Iterable<Matcher<? super T>> matchers) {
        super(matchers);
    }
    
    @Override
    public boolean matches(Object o) {
        return matches(o, true);
    }
    
    @Override
    public void describeTo(Description description) {
        describeTo(description, "or");
    }
    

    
	/**
     * Evaluates to true if ANY of the passed in matchers evaluate to true.
     */
    @Factory
    public static <T> Matcher<T> anyOf(Matcher<? super T>... matchers) {
        return anyOf(Arrays.asList(matchers));
    }

    /**
     * Evaluates to true if ANY of the passed in matchers evaluate to true.
     */
    @Factory
    public static <T> Matcher<T> anyOf(Iterable<Matcher<? super T>> matchers) {
        return new AnyOf<T>(matchers);
    }

}
