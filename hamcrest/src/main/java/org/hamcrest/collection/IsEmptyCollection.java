package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

/**
 * Tests if a collection is empty.
 *
 * @param <E> the collection element type
 */
public class IsEmptyCollection<E> extends TypeSafeMatcher<Collection<? extends E>> {

    /**
     * Constructor, best called from {@link #empty()} or
     * {@link #emptyCollectionOf(Class)}.
     */
    public IsEmptyCollection() {
    }

    @Override
    public boolean matchesSafely(Collection<? extends E> item) {
        return item.isEmpty();
    }

    @Override
    public void describeMismatchSafely(Collection<? extends E> item, Description mismatchDescription) {
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
     * @param <E>
     *     the matcher type.
     * @return The matcher.
     */
    public static <E> Matcher<Collection<? extends E>> empty() {
        return new IsEmptyCollection<>();
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s matching examined collections whose <code>isEmpty</code>
     * method returns <code>true</code>.
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyCollectionOf(String.class)))</pre>
     *
     * @param <E>
     *     the matcher type.
     * @param unusedToForceReturnType
     *     the type of the collection's content
     * @return The matcher.
     */
    @SuppressWarnings({"unchecked", "UnusedParameters"})
    public static <E> Matcher<Collection<E>> emptyCollectionOf(Class<E> unusedToForceReturnType) {
      return (Matcher)empty();
    }

}
