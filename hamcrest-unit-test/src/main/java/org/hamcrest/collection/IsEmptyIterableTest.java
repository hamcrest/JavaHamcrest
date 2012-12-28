package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;

public class IsEmptyIterableTest extends AbstractMatcherTest<Iterable<String>> {

    @Override
    protected Matcher<Iterable<String>> createMatcher() {
        return emptyIterable();
    }

    public void testMatchesAnEmptyIterable() {
        assertMatches("empty iterable", createMatcher(), emptyCollection());
    }

    public void testDoesNotMatchAnIterableWithItems() {
        assertDoesNotMatch("iterable with an item", createMatcher(), collectionOfValues());
    }

    public void testHasAReadableDescription() {
        assertDescription("an empty iterable", createMatcher());
    }

    public void testCompiles() {
        needs(IsEmptyIterable.emptyIterableOf(String.class));
    }

    private void needs(@SuppressWarnings("unused") Matcher<Iterable<String>> bar) { }

    private static Collection<String> collectionOfValues() {
        return new ArrayList<String>(asList("one", "three"));
    }

    private static Collection<String> emptyCollection() {
        return new ArrayList<String>();
    }
}
