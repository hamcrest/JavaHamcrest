package org.hamcrest.collection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

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
