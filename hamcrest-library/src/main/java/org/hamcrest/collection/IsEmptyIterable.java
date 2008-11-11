package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Tests if collection is empty.
 */
public class IsEmptyIterable<E> extends TypeSafeMatcher<Iterable<E>> {

    @Override
    public boolean matchesSafely(Iterable<E> iterable) {
        return !iterable.iterator().hasNext();
    }

    public void describeTo(Description description) {
        description.appendText("an empty iterable");
    }

    /**
     * Matches an empty iterable.
     */
    @Factory
    public static <E> Matcher<Iterable<E>> emptyIterable() {
        return new IsEmptyIterable<E>();
    }
}
