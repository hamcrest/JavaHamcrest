package org.hamcrest.internal;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InPlacePermutatorTest extends TestCase {

    @SafeVarargs
    private static <T> List<T[]> getPermutations(T... items) {
        return getFirstPermutations(Integer.MAX_VALUE, items);
    }

    @SafeVarargs
    private static <T> List<T[]> getFirstPermutations(int n, T... items) {
        final T[] currentPermutation = items.clone();
        final List<T[]> permutations = new ArrayList<>();
        final AtomicInteger remaining = new AtomicInteger(n);
        new InPlacePermutator<T>(currentPermutation) {
            @Override
            protected boolean handlePermutation() {
                permutations.add(currentPermutation.clone());
                return (remaining.decrementAndGet() == 0);
            }
        }.iteratePermutations();
        return permutations;
    }

    public void testThereIsNoPermutationOfEmpty() {
        List<Object[]> permutations = (List<Object[]>) getPermutations();
        Assert.assertTrue(permutations.isEmpty());
    }

    public void testThereIsOnePermutationOfASingleton() {
        List<String[]> permutations = getPermutations("Hello");
        String[] expected = new String[]{"Hello"};
        Assert.assertEquals(1, permutations.size());
        Assert.assertArrayEquals(expected, permutations.get(0));
    }

    public void testMorePermutations() {
        List<String[]> permutations = getPermutations("a", "b", "c");
        Assert.assertEquals(6, permutations.size());
        Assert.assertArrayEquals(new String[]{"a", "b", "c"}, permutations.get(0));
        Assert.assertArrayEquals(new String[]{"b", "a", "c"}, permutations.get(1));
        Assert.assertArrayEquals(new String[]{"c", "a", "b"}, permutations.get(2));
        Assert.assertArrayEquals(new String[]{"a", "c", "b"}, permutations.get(3));
        Assert.assertArrayEquals(new String[]{"b", "c", "a"}, permutations.get(4));
        Assert.assertArrayEquals(new String[]{"c", "b", "a"}, permutations.get(5));
    }

    public void testStopIteration() {
        List<String[]> permutations = getFirstPermutations(4, "a", "b", "c");
        Assert.assertEquals(4, permutations.size());
        Assert.assertArrayEquals(new String[]{"a", "b", "c"}, permutations.get(0));
        Assert.assertArrayEquals(new String[]{"b", "a", "c"}, permutations.get(1));
        Assert.assertArrayEquals(new String[]{"c", "a", "b"}, permutations.get(2));
        Assert.assertArrayEquals(new String[]{"a", "c", "b"}, permutations.get(3));
    }


}
