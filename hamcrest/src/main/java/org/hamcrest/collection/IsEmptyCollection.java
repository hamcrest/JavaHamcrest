package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

/**
 * Tests if collection is empty.
 */
public class IsEmptyCollection extends TypeSafeMatcher<Collection<?>> {

    @Override
    public boolean matchesSafely(Collection<?> item) {
        return item.isEmpty();
    }

    @Override
    public void describeMismatchSafely(Collection<?> item, Description mismatchDescription) {
      mismatchDescription.appendValue(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an empty collection");
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s matching examined collections whose <code>isEmpty</code>
     * method returns <code>true</code>.
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(empty()))</pre>
     * 
     */
    public static Matcher<Collection<?>> empty() {
        return new IsEmptyCollection();
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s matching examined collections whose <code>isEmpty</code>
     * method returns <code>true</code>.
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyCollectionOf(String.class)))</pre>
     * 
     * @param unusedToForceReturnType
     *     the type of the collection's content
     *
     * @deprecated This method is superfluous. Use {@link #empty()} instead.
     */
    @Deprecated
    @SuppressWarnings({"UnusedParameters"})
    public static Matcher<Collection<?>> emptyCollectionOf(Class<?> unusedToForceReturnType) {
      return empty();
    }
}
