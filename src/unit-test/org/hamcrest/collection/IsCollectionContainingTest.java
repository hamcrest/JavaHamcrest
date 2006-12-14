package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.collection.IsCollectionContaining.collectionContaining;
import static org.hamcrest.collection.IsCollectionContaining.collectionContainingAllOf;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import static java.util.Arrays.asList;

public class IsCollectionContainingTest extends AbstractMatcherTest {

    protected Matcher<?> createMatcher() {
        return collectionContaining(equalTo("irrelevant"));
    }

    public void testMatchesACollectionThatContainsAnElementMatchingTheGivenMatcher() {
        assertMatches("should matches list that contains 'a'",
                collectionContaining(equalTo("a")), asList("a", "b", "c"));
    }

    public void testDoesNotMatchCollectionThatDoesntContainAnElementMatchingTheGivenMatcher() {
        assertDoesNotMatch("should not matches list that doesn't contain 'a'",
                collectionContaining(equalTo("a")), asList("b", "c"));
        assertDoesNotMatch("should not matches empty list",
                collectionContaining(equalTo("a")), new ArrayList<String>());
    }

    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not matches null", collectionContaining(equalTo("a")), null);
    }

    public void testHasAReadableDescription() {
        assertDescription("a collection containing \"a\"", collectionContaining(equalTo("a")));
    }

    public void testMatchesAllItemsInCollection() {
        assertMatches("should match list containing all of items",
                collectionContainingAllOf(equalTo("a"), equalTo("b"), equalTo("c")),
                asList("a", "b", "c"));
        assertMatches("should match list containing all of items in any order",
                collectionContainingAllOf(equalTo("a"), equalTo("b"), equalTo("c")),
                asList("c", "b", "a"));
        assertMatches("should match list containing all of items plus others",
                collectionContainingAllOf(equalTo("a"), equalTo("b"), equalTo("c")),
                asList("e", "c", "b", "a", "d"));
        assertDoesNotMatch("should not match list unless it contains all items",
                collectionContainingAllOf(equalTo("a"), equalTo("b"), equalTo("c")),
                asList("e", "c", "b", "d")); // 'a' missing
    }
}
