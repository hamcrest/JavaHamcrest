package org.hamcrest.function;

/**
 * <p>
 * Anticipated function from Java 8+/Guava used in Hamcrest context to access an aspect of the matched object
 * for comparison.
 * </p>
 *
 * @param <T> type of value to transform; typically the matched object
 * @param <R> type of the target value to transform to
 */
public interface Function<T, R> {
    /**
     * Applies this function to the given argument.
     *
     * @param input the function argument
     * @return the function result
     */
    R apply(T input);
}
