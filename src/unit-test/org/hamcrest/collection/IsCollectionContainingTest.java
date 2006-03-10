package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class IsCollectionContainingTest extends AbstractMatcherTest {
    Matcher matcher = new IsCollectionContaining(new IsEqual("a"));

    public void testMatchesACollectionThatContainsAnElementMatchingTheGivenMatcher() {
        assertMatches("should match list that contains 'a'",
                matcher, collectionOf(new String[]{"a", "b", "c"}));
    }

    public void testDoesNotMatchCollectionThatDoesntContainAnElementMatchingTheGivenMatcher() {
        assertDoesNotMatch("should not match list that doesn't contain 'a'",
                matcher, collectionOf(new String[]{"b", "c"}));
        assertDoesNotMatch("should not match empty list",
                matcher, emptyCollection());
    }

    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not match null", matcher, null);
    }

    public void testDoesNotMatchObjectThatIsNotCollections() {
        assertDoesNotMatch("should not match empty list", matcher, "not a collection");
    }

    public void testHasAReadableDescription() {
        Matcher matcher = new IsCollectionContaining(new IsEqual("a"));

        assertDescription("a collection containing eq(\"a\")", matcher);
    }

    private Collection collectionOf(String[] abc) {
        return Arrays.asList(abc);
    }

    private Collection emptyCollection() {
        return Collections.EMPTY_LIST;
    }
}
