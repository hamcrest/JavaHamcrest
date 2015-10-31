package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.Collections;

import static org.hamcrest.collection.IsMapWithEntries.hasEntries;

public class IsMapWithEntriesTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasEntries(Matchers.empty());
    }

    public void testDoesNotMatchNull() {
        assertMismatchDescription("was null", hasEntries(Matchers.empty()), null);
    }

    public void testDoesNotMatchAMapWhoseEntriesDoNotSatisfyTheEntriesMatcher() {
        assertMismatchDescription("map entries collection size was <0>", hasEntries(Matchers.hasSize(1)), Collections.emptyMap());
    }

    public void testMatchesAMapWhoseEntriesSatisfyTheEntriesMatcher() {
        assertMatches(hasEntries(Matchers.hasSize(1)), Collections.singletonMap("k", "v"));
    }

    public void testHasReadableDescription() {
        assertDescription("a map with entries an empty collection", hasEntries(Matchers.empty()));
    }

}
