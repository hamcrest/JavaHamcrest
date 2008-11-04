package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.DiagnosingIterableMatcher;

/**
 * Tests if collection is empty.
 */
public class IsEmptyIterable<E> extends DiagnosingIterableMatcher<E> {

    @Override
    public boolean matchesSafely(Iterable<E> iterable, Description mismatchDescription) {
        if (iterable.iterator().hasNext()) {
            mismatchDescription.appendValue(iterable);
            mismatchDescription.appendText(" was not empty.");
            return false;
        }
        return true;
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
