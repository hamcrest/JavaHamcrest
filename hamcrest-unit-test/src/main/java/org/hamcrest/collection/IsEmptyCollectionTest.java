package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

public class IsEmptyCollectionTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return empty();
    }

    public void testMatchesAnEmptyCollection() {
        assertMatches("empty collection", empty(), Arrays.asList());
    }

    public void testDoesNotMatchACollectionWithAnItem() {
        assertMismatchDescription("<[one, three]>", is(empty()), collectionOfValues());
    }

    public void testHasAReadableDescription() {
        assertDescription("an empty collection", empty());
    }

    private Collection<Object> collectionOfValues() {
      return new ArrayList<Object>() {{ add("one"); add("three"); }};
    }
}
