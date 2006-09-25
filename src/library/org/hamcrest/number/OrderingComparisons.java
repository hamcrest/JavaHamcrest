package org.hamcrest.number;

import static org.hamcrest.core.IsEqual.eq;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.Or.or;
import static org.hamcrest.core.DescribedAs.describedAs;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class OrderingComparisons {
    @Factory
    public static <T extends Comparable<T>> Matcher<T> greaterThan(T value) {
        return new IsGreaterThan<T>(value);
    }
    
    @Factory
    public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T value) {
        return describedAs("a value greater than or equal to %0", 
                           or(greaterThan(value), eq(value)),
                           value);
    }
    
    @Factory
    public static <T extends Comparable<T>> Matcher<T> lessThan(T value) {
        return describedAs("a value less than %0", 
                           not(greaterThanOrEqualTo(value)),
                           value);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T value) {
        return describedAs("a value less than or equal to %0",
                           not(greaterThan(value)),
                           value);
    }
}
