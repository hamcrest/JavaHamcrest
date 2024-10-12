package org.hamcrest.collection;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class IsMapContainingTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return IsMapContaining.hasEntry("irrelevant", "irrelevant");
    }

    @Test
    public void testMatchesMapContainingMatchingKeyAndValue() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);

        assertMatches("matcherA", hasEntry(equalTo("a"), equalTo(1)), map);
        assertMatches("matcherB", hasEntry(equalTo("b"), equalTo(2)), map);
        assertMismatchDescription("map was [<a=1>, <b=2>]", hasEntry(equalTo("c"), equalTo(3)), map);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testMatchesMapContainingMatchingKeyAndValueWithoutGenerics() {
        Map map = new HashMap();
        map.put("a", 1);
        map.put("b", 2);

        assertMatches("matcherA", hasEntry(equalTo("a"), equalTo(1)), map);
        assertMatches("matcherB", hasEntry(equalTo("b"), equalTo(2)), map);
        assertFalse(hasEntry(equalTo("c"), equalTo(3)).matches(map), "matcherC"); // working around generics problem
    }

    @Test
    public void testDoesNotMatchNull() {
        assertMismatchDescription("was null", hasEntry(anything(), anything()), null);
    }

    @Test
    public void testHasReadableDescription() {
        assertDescription("map containing [\"a\"-><2>]", hasEntry(equalTo("a"), (equalTo(2))));
    }

    @Test
    public void testTypeVariance() {
        Map<String, Number> m = new HashMap<>();
        Integer foo = 6;
        m.put("foo", foo);
        assertThat(m, hasEntry("foo", foo));
    }

}
