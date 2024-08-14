package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.collection.ArrayMatching;

import java.util.Arrays;

/**
 * Calculates the logical disjunction of multiple matchers. Evaluation is shortcut, so
 * subsequent matchers are not called if an earlier matcher returns <code>true</code>.
 *
 * @param <T> the matched value type
 */
public class AnyOf<T> extends ShortcutCombination<T> {

    /**
     * Constructor, best called from {@link #anyOf(Matcher[])}.
     * @param matchers the matchers
     * @see #anyOf(Matcher[]) 
     */
    @SafeVarargs
    public AnyOf(Matcher<? super T> ... matchers) {
        this(Arrays.asList(matchers));
    }

    /**
     * Constructor, best called from {@link #anyOf(Iterable)}.
     * @param matchers the matchers
     * @see #anyOf(Iterable) 
     */
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
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     *
     * @param <T>
     *     the matcher type.
     * @param matchers
     *     any the matchers must pass.
     * @return The matcher.
     */
    public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> matchers) {
        return new AnyOf<>(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     *
     * @param <T>
     *     the matcher type.
     * @param matchers
     *     any the matchers must pass.
     * @return The matcher.
     */
    @SafeVarargs
    public static <T> AnyOf<T> anyOf(Matcher<? super T>... matchers) {
        return anyOf((Iterable) Arrays.asList(matchers));
    }

}
