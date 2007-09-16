package org.hamcrest.number;

import org.hamcrest.Matcher;


/**
 * Use {@link OrderingComparison} instead.
 */
@Deprecated
public class OrderingComparisons {
    public static <T extends Comparable<T>> Matcher<T> greaterThan(T value) {
        return OrderingComparison.greaterThan(value);
    }

    public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T value) {
        return OrderingComparison.greaterThanOrEqualTo(value);
    }

    public static <T extends Comparable<T>> Matcher<T> lessThan(T value) {
        return OrderingComparison.lessThan(value);
    }

    public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T value) {
        return OrderingComparison.lessThanOrEqualTo(value);
    }
}
