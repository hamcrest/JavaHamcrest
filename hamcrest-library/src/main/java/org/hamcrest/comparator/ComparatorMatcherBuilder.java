package org.hamcrest.comparator;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Comparator;

import static java.lang.Integer.signum;

public final class ComparatorMatcherBuilder<T> {

    private final Comparator<T> comparator;
    private final boolean includeComparatorInDescription;

    /**
     * Creates a matcher factory for matchers of {@code Comparable}s.
     * For example:
     * <pre>assertThat(1, ComparatorMatcherBuilder.&lt;Integer&gt;usingNaturalOrdering().lessThanOrEqualTo(1))</pre>
     */
    public static <T extends Comparable<T>> ComparatorMatcherBuilder<T> usingNaturalOrdering() {
        return new ComparatorMatcherBuilder<T>(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }
        }, false);
    }

    /**
     * Creates a matcher factory for matchers of {@code Comparators}s of {@code T}.
     * For example:
     * <pre>assertThat(5, comparedBy(new Comparator&lt;Integer&gt;() {
     * public int compare(Integer o1, Integer o2) {
     * return -o1.compareTo(o2);
     * }
     * }).lessThan(4))</pre>
     */
    public static <T> ComparatorMatcherBuilder<T> comparedBy(Comparator<T> comparator) {
        return new ComparatorMatcherBuilder<T>(comparator, true);
    }

    private ComparatorMatcherBuilder(Comparator<T> comparator, boolean includeComparatorInDescription) {
        this.comparator = comparator;
        this.includeComparatorInDescription = includeComparatorInDescription;
    }

    private static final class ComparatorMatcher<T> extends TypeSafeMatcher<T> {
        private static final int LESS_THAN = -1;
        private static final int GREATER_THAN = 1;
        private static final int EQUAL = 0;

        private final Comparator<T> comparator;
        private final T expected;
        private final int minCompare;
        private final int maxCompare;
        private final boolean includeComparatorInDescription;

        private static final String[] comparisonDescriptions = {
                "less than",
                "equal to",
                "greater than"
        };

        private ComparatorMatcher(Comparator<T> comparator, T expected, int minCompare, int maxCompare, boolean includeComparatorInDescription) {
            this.comparator = comparator;
            this.expected = expected;
            this.minCompare = minCompare;
            this.maxCompare = maxCompare;
            this.includeComparatorInDescription = includeComparatorInDescription;
        }

        @Override
        public boolean matchesSafely(T actual) {
            try {
                int compare = signum(comparator.compare(actual, expected));
                return minCompare <= compare && compare <= maxCompare;
            } catch (ClassCastException e) {
                return false; // type erasure means someone can shonk in a non-T :(
            }
        }

        @Override
        public void describeMismatchSafely(T actual, Description mismatchDescription) {
            mismatchDescription.appendValue(actual).appendText(" was ")
                    .appendText(asText(comparator.compare(actual, expected)))
                    .appendText(" ").appendValue(expected);
            if (includeComparatorInDescription) {
                mismatchDescription.appendText(" when compared by ").appendValue(comparator);
            }
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("a value ").appendText(asText(minCompare));
            if (minCompare != maxCompare) {
                description.appendText(" or ").appendText(asText(maxCompare));
            }
            description.appendText(" ").appendValue(expected);
            if (includeComparatorInDescription) {
                description.appendText(" when compared by ").appendValue(comparator);
            }
        }

        private static String asText(int comparison) {
            return comparisonDescriptions[signum(comparison) + 1];
        }
    }

    /**
     * Creates a matcher of {@code T} object that matches when the examined object is
     * equal to the specified value, as reported by the {@code Comparator} used to
     * create this builder.
     * For example:
     * <pre>assertThat(1, ComparatorMatcherBuilder.&lt;Integer&gt;usingNaturalOrdering().comparesEqualTo(1))</pre>
     *
     * @param value the value which, when passed to the Comparator supplied to this builder, should return zero
     */
    public Matcher<T> comparesEqualTo(T value) {
        return new ComparatorMatcher<T>(comparator, value, ComparatorMatcher.EQUAL, ComparatorMatcher.EQUAL, includeComparatorInDescription);
    }

    /**
     * Creates a matcher of {@code T} object that matches when the examined object is
     * greater than the specified value, as reported by the {@code Comparator} used to
     * create this builder.
     * For example:
     * <pre>assertThat(2, ComparatorMatcherBuilder.&lt;Integer&gt;usingNaturalOrdering().greaterThan(1))</pre>
     *
     * @param value the value which, when passed to the Comparator supplied to this builder, should return greater
     *              than zero
     */
    public Matcher<T> greaterThan(T value) {
        return new ComparatorMatcher<T>(comparator, value, ComparatorMatcher.GREATER_THAN, ComparatorMatcher.GREATER_THAN, includeComparatorInDescription);
    }

    /**
     * Creates a matcher of {@code T} object that matches when the examined object is
     * greater than or equal to the specified value, as reported by the {@code Comparator} used to
     * create this builder.
     * For example:
     * <pre>assertThat(1, ComparatorMatcherBuilder.&lt;Integer&gt;usingNaturalOrdering().greaterThanOrEqualTo(1))</pre>
     *
     * @param value the value which, when passed to the Comparator supplied to this builder, should return greater
     *              than or equal to zero
     */
    public Matcher<T> greaterThanOrEqualTo(T value) {
        return new ComparatorMatcher<T>(comparator, value, ComparatorMatcher.EQUAL, ComparatorMatcher.GREATER_THAN, includeComparatorInDescription);
    }

    /**
     * Creates a matcher of {@code T} object that matches when the examined object is
     * less than the specified value, as reported by the {@code Comparator} used to
     * create this builder.
     * For example:
     * <pre>assertThat(1, ComparatorMatcherBuilder.&lt;Integer&gt;usingNaturalOrdering().lessThan(2))</pre>
     *
     * @param value the value which, when passed to the Comparator supplied to this builder, should return less
     *              than zero
     */
    public Matcher<T> lessThan(T value) {
        return new ComparatorMatcher<T>(comparator, value, ComparatorMatcher.LESS_THAN, ComparatorMatcher.LESS_THAN, includeComparatorInDescription);
    }

    /**
     * Creates a matcher of {@code T} object that matches when the examined object is
     * less than or equal to the specified value, as reported by the {@code Comparator} used to
     * create this builder.
     * For example:
     * <pre>assertThat(1, ComparatorMatcherBuilder.&lt;Integer&gt;usingNaturalOrdering().lessThanOrEqualTo(1))</pre>
     *
     * @param value the value which, when passed to the Comparator supplied to this builder, should return less
     *              than or equal to zero
     */
    public Matcher<T> lessThanOrEqualTo(T value) {
        return new ComparatorMatcher<T>(comparator, value, ComparatorMatcher.LESS_THAN, ComparatorMatcher.EQUAL, includeComparatorInDescription);
    }
}
