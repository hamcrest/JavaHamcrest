package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsCollectionContaining;

import java.util.Arrays;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matches if an array contains an item satisfying a nested matcher.
 */
public class IsArrayContaining<T> extends TypeSafeMatcher<T[]> {
    private final IsCollectionContaining<T> collectionMatcher;
    private final Matcher<? super T> elementMatcher;

    public IsArrayContaining(Matcher<? super T> elementMatcher) {
    	this.collectionMatcher = new IsCollectionContaining<T>(elementMatcher);
        this.elementMatcher = elementMatcher;
    }

    @Override
    public boolean matchesSafely(T[] item) {
        return collectionMatcher.matches(Arrays.asList(item));
    }
    
    @Override
    public void describeMismatchSafely(T[] item, Description mismatchDescription) {
        collectionMatcher.describeMismatch(Arrays.asList(item), mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description
            .appendText("an array containing ")
            .appendDescriptionOf(elementMatcher);
    }

    /**
     * Creates a matcher for arrays that matches when the examined array contains at least one item
     * that is matched by the specified <code>elementMatcher</code>.  Whilst matching, the traversal
     * of the examined array will stop as soon as a matching element is found.
     * For example:
     * <pre>assertThat(new String[] {"foo", "bar"}, hasItemInArray(startsWith("ba")))</pre>
     * 
     * @param elementMatcher
     *     the matcher to apply to elements in examined arrays
     */
    public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> elementMatcher) {
        return new IsArrayContaining<T>(elementMatcher);
    }

    /**
     * A shortcut to the frequently used <code>hasItemInArray(equalTo(x))</code>.
     * For example:
     * <pre>assertThat(hasItemInArray(x))</pre>
     * instead of:
     * <pre>assertThat(hasItemInArray(equalTo(x)))</pre>
     * 
     * @param element
     *     the element that should be present in examined arrays
     */
    public static <T> Matcher<T[]> hasItemInArray(T element) {
        Matcher<? super T> matcher = equalTo(element);
        return IsArrayContaining.<T>hasItemInArray(matcher);
    }
}
