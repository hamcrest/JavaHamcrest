package org.hamcrest.collection;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Matches if collection size satisfies a nested matcher.
 */
public class IsCollectionWithSize<E> extends CollectionMatcher<E> {
    private final Matcher<Integer> sizeMatcher;

    public IsCollectionWithSize(Matcher<Integer> sizeMatcher) {
        this.sizeMatcher = sizeMatcher;
    }

    @Override
    public boolean matchesSafely(Collection<E> item) {
        return sizeMatcher.matches(item.size());
    }

    public void describeTo(Description description) {
        description.appendText("a collection with size ")
            .appendDescriptionOf(sizeMatcher);
    }

    /**
     * Does collection size satisfy a given matcher?
     */
    @Factory
    public static <E> Matcher<Collection<E>> hasSize(Matcher<Integer> size) {
        return new IsCollectionWithSize<E>(size);
    }

    /**
     * This is a shortcut to the frequently used hasSize(equalTo(x)).
     *
     * For example,  assertThat(hasSize(equal_to(x)))
     *          vs.  assertThat(hasSize(x))
     */
    @Factory
    public static <E> Matcher<Collection<E>> hasSize(int size) {
        return hasSize(equalTo(size));
    }
}
