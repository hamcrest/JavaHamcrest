package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.collection.IsMapEntry.entry;
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

    public void testMatchesANumberOfExplicitEntriesInAnyOrder() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("c", 3);
        map.put("b", 2);
        map.put("a", 1);

        assertMatches(hasEntries(entry("a", Matchers.equalTo(1)), entry("b", Matchers.equalTo(2)), entry("c", Matchers.equalTo(3))), map);
    }

}
