package org.hamcrest.collection;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrderTest.WithValue;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;
import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrderTest.make;
import static org.hamcrest.collection.IsIterableContainingInOrderTest.value;
import static org.hamcrest.collection.IsIterableContainingInAnyOrderTest.Item.item;

public class IsIterableContainingInAnyOrderTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return containsInAnyOrder(1, 2);
    }

    @Test
    public void testMatchesSingleItemIterable() {
      assertMatches("single item", containsInAnyOrder(1), asList(1));
    }

    @Test
    public void testDoesNotMatchEmpty() {
        assertMismatchDescription("no item matches: <1>, <2> in []", containsInAnyOrder(1, 2), Collections.<Integer>emptyList());
    }

    @Test
    public void testMatchesIterableOutOfOrder() {
        assertMatches("Out of order", containsInAnyOrder(1, 2), asList(2, 1));
    }

    @Test
    public void testMatchesIterableInOrder() {
        assertMatches("In order", containsInAnyOrder(1, 2), asList(1, 2));
    }

    @Test
    public void testDoesNotMatchIfOneOfMultipleElementsMismatches() {
        assertMismatchDescription("not matched: <4>", containsInAnyOrder(1, 2, 3), asList(1, 2, 4));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDoesNotMatchIfThereAreMoreElementsThanMatchers() {
        final Matcher<Iterable<? extends WithValue>> helpTheCompilerOut = containsInAnyOrder(value(1), value(3));
        assertMismatchDescription("not matched: <WithValue 2>", helpTheCompilerOut, asList(make(1), make(2), make(3)));
    }

    @Test
    public void testDoesNotMatchIfThereAreMoreMatchersThanElements() {
        assertMismatchDescription("no item matches: <4> in [<1>, <2>, <3>]", containsInAnyOrder(1, 2, 3, 4), asList(1, 2, 3));
    }

    @Test
    public void testHasAReadableDescription() {
        assertDescription("iterable with items [<1>, <2>] in any order", containsInAnyOrder(1, 2));
    }

    private static final ItemValueComparator comparator = new ItemValueComparator();

    private static class ItemValueComparator implements Comparator<Item> {
        @Override
        public int compare(Item o1, Item o2) {
            return Integer.compare(o1.value, o2.value);
        }

        @Override
        public String toString() {
            return ItemValueComparator.class.getSimpleName();
        }
    }

    public static class Item {
        private final String key;
        private final int value;

        private Item(String key, int value) {
            this.key = key;
            this.value = value;
        }

        public static Item item(String key, int value) {
            return new Item(key, value);
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key + ":" + value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return Objects.equals(key, item.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }

    public void testMatchesSingleItemIterableWithComparator() {
        assertMatches("single item", containsInAnyOrder(comparator, item("key", 1)), Collections.singletonList(item("foo", 1)));
    }

    public void testDoesNotMatchEmptyWithComparator() {
        List<Integer> actual = Collections.<Integer>emptyList();
        Matcher<Iterable<? extends Item>> expected = containsInAnyOrder(comparator, item("key", 5), item("key", 10));
        assertMismatchDescription("no item matches: a value equal to <key:5> when compared by <ItemValueComparator>, a value equal to <key:10> when compared by <ItemValueComparator> in []", expected, actual);
    }

    public void testMatchesIterableOutOfOrderWithComparator() {
        List<Item> actual = asList(item("foo", 2), item("bar", 1));
        Matcher<Iterable<? extends Item>> expected = containsInAnyOrder(comparator, item("key", 1), item("key", 2));
        assertMatches("Out of order", expected, actual);
    }

    public void testMatchesIterableInOrderWithComparator() {
        List<Item> actual = asList(item("foo", 1), item("bar", 2));
        Matcher<Iterable<? extends Item>> expected = containsInAnyOrder(comparator, item("key", 1), item("key", 2));
        assertMatches("In order", expected, actual);
    }

    public void testDoesNotMatchIfOneOfMultipleElementsMismatchesWithComparator() {
        List<Item> actual = asList(item("foo", 1), item("bar", 2), item("baz", 4));
        Matcher<Iterable<? extends Item>> expected = containsInAnyOrder(comparator, item("key", 1), item("key", 2), item("key", 3));
        assertMismatchDescription("not matched: <baz:4>", expected, actual);
    }

    public void testDoesNotMatchIfThereAreMoreElementsThanMatchersWithComparator() {
        List<Item> actual = asList(item("foo", 1), item("bar", 2), item("baz", 3));
        final Matcher<Iterable<? extends Item>> expected = containsInAnyOrder(comparator, item("key", 1), item("key", 3));
        assertMismatchDescription("not matched: <bar:2>", expected, actual);
    }

    public void testDoesNotMatchIfThereAreMoreMatchersThanElementsWithComparator() {
        List<Item> actual = asList(item("foo", 1), item("bar", 2));
        Matcher<Iterable<? extends Item>> expected = containsInAnyOrder(comparator, item("key", 1), item("key", 2), item("key", 3));
        assertMismatchDescription("no item matches: a value equal to <key:3> when compared by <ItemValueComparator> in [<foo:1>, <bar:2>]", expected, actual);
    }

    public void testHasAReadableDescriptionWithComparator() {
        assertDescription("iterable with items [a value equal to <foo:1> when compared by <ItemValueComparator>, a value equal to <bar:2> when compared by <ItemValueComparator>] in any order",
            containsInAnyOrder(comparator, item("foo", 1), item("bar", 2)));
    }
}
