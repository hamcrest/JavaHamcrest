package org.hamcrest.collection;

import org.hamcrest.Description;
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
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyIterable()))</pre>
     * 
     */
    public static <E> Matcher<Iterable<? extends E>> emptyIterable() {
        return new IsEmptyIterable<E>();
    }

    /**
     * Creates a matcher for {@link Iterable}s matching examined iterables that yield no items.
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyIterableOf(String.class)))</pre>
     * 
     * @param unusedToForceReturnType
     *     the type of the iterable's content
     */
    @SuppressWarnings({"unchecked", "UnusedParameters"})
    public static <E> Matcher<Iterable<E>> emptyIterableOf(Class<E> unusedToForceReturnType) {
      return (Matcher)emptyIterable();
    }
}
