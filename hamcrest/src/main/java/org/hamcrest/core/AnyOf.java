/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Arrays;

/**
 * Calculates the logical disjunction of multiple matchers. Evaluation is shortcut, so
 * subsequent matchers are not called if an earlier matcher returns <code>true</code>.
 */
public class AnyOf<T> extends ShortcutCombination<T> {

    @SafeVarargs
    public AnyOf(Matcher<? super T> ... matchers) {
        this(Arrays.asList(matchers));
    }

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
     */
    public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> matchers) {
        return new AnyOf<>(matchers);
    }
    
    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     */
    @SafeVarargs
    public static <T> AnyOf<T> anyOf(Matcher<? super T>... matchers) {
        return anyOf(Arrays.asList(matchers));
    }
}
