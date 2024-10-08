package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsIterableContaining;

import static java.util.Arrays.asList;

/**
 * Matches if an array contains an item satisfying a nested matcher.
 *
 * @param <T> the array element type
 */
public class HasItemInArray<T> extends TypeSafeMatcher<T[]> {

    private final Matcher<? super T> elementMatcher;
    private final TypeSafeDiagnosingMatcher<Iterable<? extends T>> collectionMatcher;

    /**
     * Constructor, best called from {@link ArrayMatching}.
     * @param elementMatcher matcher for the expected item
     * @see ArrayMatching#hasItemInArray(Matcher)
     */
    public HasItemInArray(Matcher<? super T> elementMatcher) {
        this.elementMatcher = elementMatcher;
        this.collectionMatcher = new IsIterableContaining<>(elementMatcher);
    }

    @Override
    public boolean matchesSafely(T[] actual) {
        return collectionMatcher.matches(asList(actual));
    }

    @Override
    public void describeMismatchSafely(T[] actual, Description mismatchDescription) {
        collectionMatcher.describeMismatch(asList(actual), mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description
            .appendText("an array containing ")
            .appendDescriptionOf(elementMatcher);
    }

}
