package org.hamcrest.comparator;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Comparator;

import static java.lang.Integer.signum;

public final class ComparatorMatcherBuilder<T> {

    private final Comparator<T> comparator;

    /**
     * Creates a matcher factory for matchers of {@code Comparable}s.
     * <p/>
     * For example:
     * <pre>assertThat(1, ComparatorMatcherBuilder.<Integer>usingDefaultComparison().lessThanOrEqualTo(1))</pre>
     */
    public static <T extends Comparable<T>> ComparatorMatcherBuilder<T> usingDefaultComparison() {
        return new ComparatorMatcherBuilder<T>(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * Creates a matcher factory for matchers of {@code Comparators}s of {@code T}.
     * <p/>
     * For example:
     * <pre>assertThat(5, comparedBy(new Comparator<Integer>() {
     * public int compare(Integer o1, Integer o2) {
     * return -o1.compareTo(o2);
     * }
     * }).lessThan(4))</pre>
     */
    public static <T> ComparatorMatcherBuilder<T> comparedBy(Comparator<T> comparator) {
        return new ComparatorMatcherBuilder<T>(comparator);
    }

    private ComparatorMatcherBuilder(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private static final class ComparatorMatcher<T> extends TypeSafeMatcher<T> {
        private static final int LESS_THAN = -1;
        private static final int GREATER_THAN = 1;
        private static final int EQUAL = 0;

        private final Comparator<T> comparator;
        private final T expected;
        private final int minCompare, maxCompare;

        private static final String[] comparisonDescriptions = {
                "less than",
                "equal to",
                "greater than"
        };

        private ComparatorMatcher(Comparator<T> comparator, T expected, int minCompare, int maxCompare) {
            this.comparator = comparator;
            this.expected = expected;
            this.minCompare = minCompare;
            this.maxCompare = maxCompare;
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
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("a value ").appendText(asText(minCompare));
            if (minCompare != maxCompare) {
                description.appendText(" or ").appendText(asText(maxCompare));
            }
            description.appendText(" ").appendValue(expected);
        }

        private static String asText(int comparison) {
            return comparisonDescriptions[signum(comparison) + 1];
        }
    }

    /**
     * Creates a matcher of {@code T} object that matches when the examined object is
     * equal to the specified value, as reported by the {@code Comparator} used to
     * create this builder.
     * <p/>
     * For example:
     * <pre>assertThat(1, ComparatorMatcherBuilder.<Integer>usingDefaultComparison().comparesEqualTo(1))</pre>
     *
     * @param value the value which, when passed to the Comparator supplied to this builder, should return zero
     */
    @Factory
    public Matcher<T> comparesEqualTo(T value) {
        return new ComparatorMatcher<T>(comparator, value, ComparatorMatcher.EQUAL, ComparatorMatcher.EQUAL);
    }

    /**
     * Creates a matcher of {@code T} object that matches when the examined object is
     * greater than the specified value, as reported by the {@code Comparator} used to
     * create this builder.
     * <p/>
     * For example:
     * <pre>assertThat(2, ComparatorMatcherBuilder.<Integer>usingDefaultComparison().greaterThan(1))</pre>
     *
     * @param value the value which, when passed to the Comparator supplied to this builder, should return greater
     *              than zero
     */
    @Factory
    public Matcher<T> greaterThan(T value) {
        return new ComparatorMatcher<T>(comparator, value, ComparatorMatcher.GREATER_THAN, ComparatorMatcher.GREATER_THAN);
    }

    /**
     * Creates a matcher of {@code T} object that matches when the examined object is
     * greater than or equal to the specified value, as reported by the {@code Comparator} used to
     * create this builder.
     * <p/>
     * For example:
     * <pre>assertThat(1, ComparatorMatcherBuilder.<Integer>usingDefaultComparison().greaterThanOrEqualTo(1))</pre>
     *
     * @param value the value which, when passed to the Comparator supplied to this builder, should return greater
     *              than or equal to zero
     */
    @Factory
    public Matcher<T> greaterThanOrEqualTo(T value) {
        return new ComparatorMatcher<T>(comparator, value, ComparatorMatcher.EQUAL, ComparatorMatcher.GREATER_THAN);
    }

    /**
     * Creates a matcher of {@code T} object that matches when the examined object is
     * less than the specified value, as reported by the {@code Comparator} used to
     * create this builder.
     * <p/>
     * For example:
     * <pre>assertThat(1, ComparatorMatcherBuilder.<Integer>usingDefaultComparison().lessThan(2))</pre>
     *
     * @param value the value which, when passed to the Comparator supplied to this builder, should return less
     *              than zero
     */
    @Factory
    public Matcher<T> lessThan(T value) {
        return new ComparatorMatcher<T>(comparator, value, ComparatorMatcher.LESS_THAN, ComparatorMatcher.LESS_THAN);
    }

    /**
     * Creates a matcher of {@code T} object that matches when the examined object is
     * less than or equal to the specified value, as reported by the {@code Comparator} used to
     * create this builder.
     * <p/>
     * For example:
     * <pre>assertThat(1, ComparatorMatcherBuilder.<Integer>usingDefaultComparison().lessThanOrEqualTo(1))</pre>
     *
     * @param value the value which, when passed to the Comparator supplied to this builder, should return less
     *              than or equal to zero
     */
    @Factory
    public Matcher<T> lessThanOrEqualTo(T value) {
        return new ComparatorMatcher<T>(comparator, value, ComparatorMatcher.LESS_THAN, ComparatorMatcher.EQUAL);
    }
}
