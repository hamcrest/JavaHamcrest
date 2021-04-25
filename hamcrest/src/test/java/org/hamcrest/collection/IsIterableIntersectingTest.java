package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Arrays;

import static org.hamcrest.collection.IsIterableIntersecting.intersectWith;

public class IsIterableIntersectingTest extends AbstractMatcherTest {
    @Override
    protected Matcher<?> createMatcher() { return intersectWith(1, 2); }

    public void testEmptyNotIntersect() {
        assertMismatchDescription("no item intersects: <1>, <2> with []", intersectWith(1, 2), Collections.<Integer>emptyList());
    }

    public void testNotIntersect() {
        assertMismatchDescription("no item intersects: <1>, <2>, <3> with [<4>, <5>]", intersectWith(1, 2, 3), Arrays.asList(4, 5));
    }

    public void testSingleIntersect() {
        assertMatches("Single intersect", intersectWith(1), Collections.singletonList(1));
    }

    public void testPartlyIntersect() {
        assertMatches("Partly intersect", intersectWith(1, 2, 3), Arrays.asList(4, 2, 5));
    }

    public void testFullyIntersect() {
        assertMatches("Fully intersect", intersectWith(1, 2, 3), Arrays.asList(3, 4, 2, 1, 5));
        assertMatches("Fully intersect", intersectWith(1, 2, 3), Arrays.asList(2, 3));
        assertMatches("Fully intersect", intersectWith(1, 2, 3), Arrays.asList(1, 2, 3));
    }

    public void testDuplicateIntersect() {
        assertMatches("Duplicate intersect", intersectWith(1, 2, 3), Arrays.asList(2, 2, 2, 4));
        assertMatches("Duplicate intersect", intersectWith(1, 2, 2, 2, 3), Arrays.asList(2, 4, 4, 4));
    }

    public void testHasAReadableDescription() {
        assertDescription("iterable with items [<1>, <2>] intersecting", intersectWith(1, 2));
    }
}
