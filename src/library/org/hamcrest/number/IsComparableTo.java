/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import static org.hamcrest.core.IsEqual.eq;
import static org.hamcrest.core.Or.or;
import org.hamcrest.Matcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;

/**
 * Is the value less than or greater than another {@link java.lang.Comparable} value?
 */
public class IsComparableTo<T extends Comparable> implements Matcher<T> {

    public static enum Operator {
        LESS_THAN,
        GREATER_THAN
    }

    private final Comparable<T> compareTo;
    private final Operator operator;

    public IsComparableTo(Comparable<T> compareTo, Operator operator) {
        this.compareTo = compareTo;
        this.operator = operator;
    }

    public boolean match(T item) {
        int comparison = compareTo.compareTo(item);
        return operator == Operator.GREATER_THAN ? comparison < 0 : comparison > 0;
    }

    public void describeTo(Description description) {
        description.appendText("a value ");
        description.appendText(operator == Operator.GREATER_THAN ? "greater than " : "less than ");
        description.appendValue(compareTo);
    }

    @Factory
    public static <T extends Comparable> Matcher<Comparable<T>> greaterThan(Comparable<T> value) {
        return comparableTo(value, Operator.GREATER_THAN);
    }

    @Factory
    public static <T extends Comparable> Matcher<Comparable<T>> greaterThanOrEqualTo(Comparable<T> value) {
        return or(greaterThan(value), eq(value));
    }

    @Factory
    public static <T extends Comparable> Matcher<Comparable<T>> lessThan(Comparable<T> value) {
        return comparableTo(value, Operator.LESS_THAN);
    }

    @Factory
    public static <T extends Comparable> Matcher<Comparable<T>> lessThanOrEqualTo(Comparable<T> value) {
        return or(lessThan(value), eq(value));
    }

    private static <T extends Comparable> Matcher<Comparable<T>> comparableTo(Comparable<T> value, Operator operator) {
        return new IsComparableTo(value, operator);
    }

}
