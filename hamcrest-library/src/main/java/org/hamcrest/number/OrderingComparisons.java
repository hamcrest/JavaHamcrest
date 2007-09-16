package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

@SuppressWarnings("unchecked")
public class OrderingComparisons {
    public static class OrderingComparison<T extends Comparable> extends TypeSafeMatcher<T> {
        private final T value;
        private final int minCompare, maxCompare;
        
        public OrderingComparison(T value, int minCompare, int maxCompare) {
            this.value = value;
            this.minCompare = minCompare;
            this.maxCompare = maxCompare;
        }

        @Override
        public boolean matchesSafely(T other) {
            int compare =  Integer.signum(value.compareTo(other));
            return minCompare <= compare && compare <= maxCompare;
        }

        public void describeTo(Description description) {
            description.appendText("a value ")
                       .appendText(comparison(minCompare));
            if (minCompare != maxCompare) {
                description.appendText(" or ")
                           .appendText(comparison(maxCompare));
            }
            description.appendText(" ")
                       .appendValue(value);
        }
        
        private String comparison(int compare) {
            if (compare > 0) {
                return "less than";
            }
            else if (compare == 0) {
                return "equal to ";
            }
            else {
                return "greater than";
            }
        }
    }
    
    @Factory
    public static <T extends Comparable<T>> Matcher<T> greaterThan(T value) {
        return new OrderingComparison<T>(value, -1, -1);
    }
    
	@Factory
    public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T value) {
        return new OrderingComparison<T>(value, -1, 0);
    }
    
    @Factory
    public static <T extends Comparable<T>> Matcher<T> lessThan(T value) {
        return new OrderingComparison<T>(value, 1, 1);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T value) {
        return new OrderingComparison<T>(value, 0, 1);
    }
}
