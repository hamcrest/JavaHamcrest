package org.hamcrest.collection;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Tests if collection is empty.
 */
public class IsEmptyCollection<C extends Collection<?>> extends TypeSafeMatcher<C> {

    @Override
    public boolean matchesSafely(C item) {
        return item.isEmpty();
    }

    @Override
    public void describeMismatchSafely(C item, Description mismatchDescription) {
      mismatchDescription.appendValue(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an empty collection");
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s matching examined collections whose <code>isEmpty</code>
     * method returns <code>true</code>.
     * <p/>
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(empty()))</pre>
     *
     */
    @Factory
    public static <C extends Collection<?>> Matcher<C> empty() {
        return new IsEmptyCollection<C>();
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s matching examined collections whose <code>isEmpty</code>
     * method returns <code>true</code>.
     * <p/>
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyCollectionOf(String.class)))</pre>
     *
     * @param type
     *     the type of the collection's content
     */
    @Factory
    public static <T> Matcher<Collection<T>> emptyCollectionOf(Class<T> type) {
        return empty();
    }
}
