package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Tests if collection is empty.
 */
public class IsEmptyIterable<E> extends TypeSafeMatcher<Iterable<? extends E>> {

    @Override
    public boolean matchesSafely(Iterable<? extends E> iterable) {
        return !iterable.iterator().hasNext();
    }
    @Override
    public void describeMismatchSafely(Iterable<? extends E> iter, Description mismatchDescription) {
        mismatchDescription.appendValueList("[", ",", "]", iter);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an empty iterable");
    }

    /**
     * Creates a matcher for {@link Iterable}s matching examined iterables that yield no items.
     * <p/>
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyIterable()))</pre>
     * 
     */
    @Factory
    public static <E> Matcher<Iterable<? extends E>> emptyIterable() {
        return new IsEmptyIterable<E>();
    }

    /**
     * Creates a matcher for {@link Iterable}s matching examined iterables that yield no items.
     * <p/>
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyIterableOf(String.class)))</pre>
     * 
     * @param type
     *     the type of the iterable's content
     */
    @Factory
    public static <E> Matcher<Iterable<E>> emptyIterableOf(Class<E> type) {
        @SuppressWarnings({ "rawtypes", "unchecked" })
        final Matcher<Iterable<E>> result = (Matcher)emptyIterable();
        return result;
    }
}
