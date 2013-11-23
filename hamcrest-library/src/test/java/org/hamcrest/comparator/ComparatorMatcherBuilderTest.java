/*  Copyright (c) 2000-2009 hamcrest.org
 */
package org.hamcrest.comparator;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.math.BigDecimal;
import java.util.Comparator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.comparator.ComparatorMatcherBuilder.comparedBy;
import static org.hamcrest.core.IsNot.not;

public class ComparatorMatcherBuilderTest extends AbstractMatcherTest {

    private final ComparatorMatcherBuilder<Integer> integerComparatorMatcherBuilder = ComparatorMatcherBuilder.usingDefaultComparison();
    private final ComparatorMatcherBuilder<Double> doubleComparatorMatcherBuilder = ComparatorMatcherBuilder.usingDefaultComparison();
    private final ComparatorMatcherBuilder<String> stringComparatorMatcherBuilder = ComparatorMatcherBuilder.usingDefaultComparison();
    private final ComparatorMatcherBuilder<BigDecimal> bigDecimalComparatorMatcherBuilder = ComparatorMatcherBuilder.usingDefaultComparison();

    @Override
    protected Matcher<Integer> createMatcher() {
        return integerComparatorMatcherBuilder.greaterThan(1);
    }

    public void testDescription() {
        assertDescription("a value greater than <1>", integerComparatorMatcherBuilder.greaterThan(1));
        assertDescription("a value equal to or greater than <1>", integerComparatorMatcherBuilder.greaterThanOrEqualTo(1));
        assertDescription("a value equal to <1>", integerComparatorMatcherBuilder.comparesEqualTo(1));
        assertDescription("a value less than or equal to <1>", integerComparatorMatcherBuilder.lessThanOrEqualTo(1));
        assertDescription("a value less than <1>", integerComparatorMatcherBuilder.lessThan(1));
    }

    public void testMismatchDescriptions() {
        assertMismatchDescription("<0> was less than <1>", integerComparatorMatcherBuilder.greaterThan(1), 0);
        assertMismatchDescription("<1> was equal to <1>", integerComparatorMatcherBuilder.greaterThan(1), 1);
        assertMismatchDescription("<1> was greater than <0>", integerComparatorMatcherBuilder.lessThan(0), 1);
        assertMismatchDescription("<2> was equal to <2>", integerComparatorMatcherBuilder.lessThan(2), 2);
    }

    public void testComparesObjectsForGreaterThan() {
        assertThat(2, integerComparatorMatcherBuilder.greaterThan(1));
        assertThat(0, not(integerComparatorMatcherBuilder.greaterThan(1)));
    }

    public void testComparesObjectsForLessThan() {
        assertThat(2, integerComparatorMatcherBuilder.lessThan(3));
        assertThat(0, integerComparatorMatcherBuilder.lessThan(1));
    }


    public void testComparesObjectsForEquality() {
        assertThat(3, integerComparatorMatcherBuilder.comparesEqualTo(3));
        assertThat("aa", stringComparatorMatcherBuilder.comparesEqualTo("aa"));
    }

    public void testAllowsForInclusiveComparisons() {
        assertThat("less", 1, integerComparatorMatcherBuilder.lessThanOrEqualTo(1));
        assertThat("greater", 1, integerComparatorMatcherBuilder.greaterThanOrEqualTo(1));
    }

    public void testSupportsDifferentTypesOfComparableObjects() {
        assertThat(1.1, doubleComparatorMatcherBuilder.greaterThan(1.0));
        assertThat("cc", stringComparatorMatcherBuilder.greaterThan("bb"));
    }

    public void testComparesBigDecimalsWithDifferentScalesCorrectlyForIssue20() {
        assertThat(new BigDecimal("10.0"), bigDecimalComparatorMatcherBuilder.greaterThanOrEqualTo(new BigDecimal("10")));
        assertThat(new BigDecimal(10), bigDecimalComparatorMatcherBuilder.greaterThanOrEqualTo(new BigDecimal("10.0")));
        assertThat(new BigDecimal("2"), bigDecimalComparatorMatcherBuilder.comparesEqualTo(new BigDecimal("2.000")));
    }

    public void testComparesCustomTypesWhoseCompareToReturnsValuesGreaterThatOne() {
        assertThat(new CustomInt(5), ComparatorMatcherBuilder.<CustomInt>usingDefaultComparison().lessThan(new CustomInt(10)));
    }

    public void testComparesByCustomComparator() {
        assertThat(5, comparedBy(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -o1.compareTo(o2);
            }
        }).lessThan(4));
    }

    public void testJavadocExamples() {
        assertThat(1, ComparatorMatcherBuilder.<Integer>usingDefaultComparison().comparesEqualTo(1));
        assertThat(2, ComparatorMatcherBuilder.<Integer>usingDefaultComparison().greaterThan(1));
        assertThat(1, ComparatorMatcherBuilder.<Integer>usingDefaultComparison().greaterThanOrEqualTo(1));
        assertThat(1, ComparatorMatcherBuilder.<Integer>usingDefaultComparison().lessThan(2));
        assertThat(1, ComparatorMatcherBuilder.<Integer>usingDefaultComparison().lessThanOrEqualTo(1));
        assertThat(5, comparedBy(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -o1.compareTo(o2);
            }
        }).lessThan(4));
    }

    private static final class CustomInt implements Comparable<CustomInt> {
        private final int value;

        public CustomInt(int value) {
            this.value = value;
        }

        public int compareTo(CustomInt other) {
            return value - other.value;
        }
    }
}
