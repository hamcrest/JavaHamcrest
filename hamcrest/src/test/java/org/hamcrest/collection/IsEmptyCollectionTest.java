package org.hamcrest.collection;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

public class IsEmptyCollectionTest extends AbstractMatcherTest {

    @Override
    protected Matcher<Collection<?>> createMatcher() {
        return empty();
    }

    @Test
    public void testMatchesAnEmptyCollection() {
        assertMatches("empty collection", createMatcher(), emptyCollection());
    }

    @Test
    public void testDoesNotMatchACollectionWithAnItem() {
        assertMismatchDescription("<[one, three]>", is(createMatcher()), collectionOfValues());
    }

    @Test
    public void testHasAReadableDescription() {
        assertDescription("an empty collection", createMatcher());
    }

    @Test
    public void testCompiles() {
        needs(IsEmptyCollection.emptyCollectionOf(String.class));
    }

    private void needs(@SuppressWarnings("unused") Matcher<Collection<String>> bar) { }

    private static Collection<String> collectionOfValues() {
        return new ArrayList<>(asList("one", "three"));
    }

    private static Collection<Integer> emptyCollection() {
        return new ArrayList<>();
    }

}
