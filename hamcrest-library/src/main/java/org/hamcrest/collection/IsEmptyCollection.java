package org.hamcrest.collection;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Tests if collection is empty.
 */
public class IsEmptyCollection<E> extends CollectionMatcher<E> {

    @Override
    public boolean matchesSafely(Collection<E> item) {
        return item.isEmpty();
    }

    public void describeTo(Description description) {
        description.appendText("an empty collection");
    }

    /**
     * Matches an empty collection.
     */
    @Factory
    public static <E> Matcher<Collection<E>> empty() {
        return new IsEmptyCollection<E>();
    }
}
