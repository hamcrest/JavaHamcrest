package org.hamcrest.core;

import java.util.Arrays;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.introspection.Combination;

/**
 * Calculates the logical disjunction of two matchers. Evaluation is
 * shortcut, so that the second matcher is not called if the first
 * matcher returns <code>true</code>.
 */
public class AnyOf<T> extends BaseMatcher<T> implements Combination {
    private final Iterable<? extends Matcher<? extends T>> matchers;

    public AnyOf(Iterable<? extends Matcher<? extends T>> matchers) {
        this.matchers = matchers;
    }

    public boolean matches(Object o) {
        for (Matcher<? extends T> matcher : matchers) {
            if (matcher.matches(o)) {
                return true;
            }
        }
        return false;
    }
    
    public void describeTo(Description description) {
    	description.appendList("(", " or ", ")", matchers);
    }
    
	public Iterable<? extends Matcher<?>> combined() {
		return matchers;
	}
	
	/**
     * Evaluates to true if ANY of the passed in matchers evaluate to true.
     */
    @Factory
    public static <T> Matcher<T> anyOf(Matcher<? extends T>... matchers) {
        return anyOf(Arrays.asList(matchers));
    }

    /**
     * Evaluates to true if ANY of the passed in matchers evaluate to true.
     */
    @Factory
    public static <T> Matcher<T> anyOf(Iterable<? extends Matcher<? extends T>> matchers) {
        return new AnyOf<T>(matchers);
    }
}
