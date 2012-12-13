package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

public class IsEmptyCollectionTest extends AbstractMatcherTest {

    @Override
    protected Matcher<Collection<?>> createMatcher() {
        return empty();
    }

    public void testMatchesAnEmptyCollection() {
        assertMatches("empty collection", createMatcher(), emptyCollection());
    }

    public void testDoesNotMatchACollectionWithAnItem() {
        assertMismatchDescription("<[one, three]>", is(createMatcher()), collectionOfValues());
    }

    public void testHasAReadableDescription() {
        assertDescription("an empty collection", createMatcher());
    }

    public void testCompiles() {
        needs(IsEmptyCollection.emptyCollectionOf(String.class));
    }

    private void needs(@SuppressWarnings("unused") Matcher<Collection<String>> bar) { }
    
    private static Collection<String> collectionOfValues() {
        return new ArrayList<String>(asList("one", "three"));
    }

    private static Collection<Integer> emptyCollection() {
        return new ArrayList<Integer>();
    }
}