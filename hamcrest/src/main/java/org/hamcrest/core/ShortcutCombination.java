package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

abstract class ShortcutCombination<T> extends BaseMatcher<T> {

    private final Iterable<Matcher<? super T>> matchers;

    public ShortcutCombination(Iterable<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public abstract boolean matches(Object o);

    @Override
    public abstract void describeTo(Description description);

    /**
     * Evaluates the argument <var>o</var> against the delegate matchers.
     *
     * Evaluation will stop at the first matcher that evaluates to the value of the
     * <code>shortcut</code> argument.
     *
     * @param o the value to check
     * @param shortcut the match result to be checked against all delegate matchers
     * @return the value of <var>shortcut</var> if all delegate matchers give the same value
     */
    protected boolean matches(Object o, boolean shortcut) {
        for (Matcher<? super T> matcher : matchers) {
            if (matcher.matches(o) == shortcut) {
                return shortcut;
            }
        }
        return !shortcut;
    }

    /**
     * Describe this matcher to <var>description</var>
     * @param description the description target
     * @param operator the separate to use when joining the matcher descriptions
     */
    public void describeTo(Description description, String operator) {
        description.appendList("(", " " + operator + " ", ")", matchers);
    }

}
