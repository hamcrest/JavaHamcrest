package org.hamcrest.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Calculates the logical disjunction of multiple matchers. Evaluation is shortcut, so
 * subsequent matchers are not called if an earlier matcher returns <code>true</code>.
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
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * <p/>
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     */
    @Factory
    public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> matchers) {
        return new AnyOf<T>(matchers);
    }
    
    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * <p/>
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     */
    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<? super T>... matchers) {
        return anyOf(Arrays.asList(matchers));
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * <p/>
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     */
    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        return anyOf(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * <p/>
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     */
    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        return anyOf(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * <p/>
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     */
    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(fourth);
        return anyOf(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * <p/>
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     */
    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(fourth);
        matchers.add(fifth);
        return anyOf(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * <p/>
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     */
    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth, Matcher<? super T> sixth) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(fourth);
        matchers.add(fifth);
        matchers.add(sixth);
        return anyOf(matchers);
    }
}
