package org.hamcrest.core;

import java.util.Arrays;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Calculates the logical conjunction of two matchers. Evaluation is
 * shortcut, so that the second matcher is not called if the first
 * matcher returns <code>false</code>.
 */
public class AllOf<T> extends ShortcutCombination<T> {
    public AllOf(Iterable<Matcher<? super T>> matchers) {
        super(matchers);
    }

    @Override
    protected boolean shortcut() {
        return false;
    }
    
	/**
     * Evaluates to true only if ALL of the passed in matchers evaluate to true.
     */
    @Factory
    public static <T> Matcher<T> allOf(Matcher<? super T>... matchers) {
        return allOf(Arrays.asList(matchers));
    }

    /**
     * Evaluates to true only if ALL of the passed in matchers evaluate to true.
     */
    @Factory
    public static <T> Matcher<T> allOf(Iterable<Matcher<? super T>> matchers) {
        return new AllOf<T>(matchers);
    }
}
