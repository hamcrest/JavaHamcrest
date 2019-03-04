package org.hamcrest.internal;

/**
 * Uses the Heap's algorithm (https://en.wikipedia.org/wiki/Heap%27s_algorithm) to produce all permutations of elements
 * of the given array.
 *
 * The elements are swapped in-place in the provided array. Each permutation is yielded by calling handlePermutation(),
 * which can return false to signal that the listing should stop.
 */
public abstract class InPlacePermutator<T> {

    private final T[] items;
    private final int size;

    protected InPlacePermutator(T[] items) {
        this.size = items.length;
        this.items = items;
    }

    /**
     * @return true if iteration was interrupted before reaching the end
     */
    public boolean iteratePermutations() {
        if (size < 1) {
            return false;
        }
        return iterateInternal(size);
    }

    /**
     * @return true if iteration should be interrupted
     */
    protected abstract boolean handlePermutation();

    private boolean iterateInternal(int n) {
        if (n == 1) {
            return handlePermutation();
        } else {
            for (int i = 0; i < n - 1; i++) {
                boolean shouldStop = iterateInternal(n - 1);
                if (shouldStop) {
                    return true;
                }
                if (n % 2 == 0) {
                    swap(i, n - 1);
                } else {
                    swap(0, n - 1);
                }
            }
            return iterateInternal(n - 1);
        }
    }

    private void swap(int i, int j) {
        T temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

}
