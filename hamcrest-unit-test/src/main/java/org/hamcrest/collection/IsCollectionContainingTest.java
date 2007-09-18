package org.hamcrest.collection;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsCollectionContaining.hasItem;
import static org.hamcrest.collection.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsCollectionContainingTest extends AbstractMatcherTest {
    protected Matcher<?> createMatcher() {
        return hasItem(equalTo("irrelevant"));
    }

    public void testMatchesACollectionThatContainsAnElementMatchingTheGivenMatcher() {
        Matcher<Iterable<String>> itemMatcher = hasItem(equalTo("a"));
        
        assertMatches("should match list that contains 'a'",
                itemMatcher, asList("a", "b", "c"));
    }

    public void testDoesNotMatchCollectionThatDoesntContainAnElementMatchingTheGivenMatcher() {
        final Matcher<Iterable<String>> matcher1 = hasItem(equalTo("a"));
        assertDoesNotMatch("should not match list that doesn't contain 'a'",
                matcher1, asList("b", "c"));
        
        
        final Matcher<Iterable<String>> matcher2 = hasItem(equalTo("a"));
        assertDoesNotMatch("should not match the empty list",
                matcher2, new ArrayList<String>());
    }

    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not matches null", hasItem(equalTo("a")), null);
    }

    public void testHasAReadableDescription() {
        assertDescription("a collection containing \"a\"", hasItem(equalTo("a")));
    }

    @SuppressWarnings("unchecked")
    public void testMatchesAllItemsInCollection() {
        final Matcher<Iterable<String>> matcher1 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("should match list containing all items",
                matcher1,
                asList("a", "b", "c"));
        
        final Matcher<Iterable<String>> matcher2 = hasItems("a", "b", "c");
        assertMatches("should match list containing all items (without matchers)",
                matcher2,
                asList("a", "b", "c"));
        
        final Matcher<Iterable<String>> matcher3 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("should match list containing all items in any order",
                matcher3,
                asList("c", "b", "a"));
        
        final Matcher<Iterable<String>> matcher4 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("should match list containing all items plus others",
                matcher4,
                asList("e", "c", "b", "a", "d"));
        
        final Matcher<Iterable<String>> matcher5 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertDoesNotMatch("should not match list unless it contains all items",
                matcher5,
                asList("e", "c", "b", "d")); // 'a' missing
    }
}
