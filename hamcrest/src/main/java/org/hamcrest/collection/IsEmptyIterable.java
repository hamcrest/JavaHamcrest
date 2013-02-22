package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Tests if collection is empty.
 */
public class IsEmptyIterable extends TypeSafeMatcher<Iterable<?>> {

    @Override
    public boolean matchesSafely(Iterable<?> iterable) {
        return !iterable.iterator().hasNext();
    }
    @Override
    public void describeMismatchSafely(Iterable<?> iter, Description mismatchDescription) {
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
    public static Matcher<Iterable<?>> emptyIterable() {
        return new IsEmptyIterable();
    }

    /**
     * Creates a matcher for {@link Iterable}s matching examined iterables that yield no items.
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyIterableOf(String.class)))</pre>
     * 
     * @param unusedToForceReturnType
     *     the type of the iterable's content
     *
     * @deprecated This method is superfluous. Use {@link #emptyIterable()}.
     */
    @Deprecated
    @SuppressWarnings({"UnusedParameters"})
    public static Matcher<Iterable<?>> emptyIterableOf(Class<?> unusedToForceReturnType) {
      return emptyIterable();
    }
}
