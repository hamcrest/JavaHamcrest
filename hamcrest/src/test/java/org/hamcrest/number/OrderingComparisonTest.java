package org.hamcrest.number;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.test.MatcherAssertions.assertDescription;
import static org.hamcrest.test.MatcherAssertions.assertMismatchDescription;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.number.OrderingComparison.*;

public class OrderingComparisonTest extends AbstractMatcherTest {

    @Override
    protected Matcher<Integer> createMatcher() {
        return greaterThan(1);
    }

    @Test
    public void testDescription() {
      assertDescription("a value greater than <1>", greaterThan(1));
      assertDescription("a value equal to or greater than <1>", greaterThanOrEqualTo(1));
      assertDescription("a value equal to <1>", comparesEqualTo(1));
      assertDescription("a value less than or equal to <1>", lessThanOrEqualTo(1));
      assertDescription("a value less than <1>", lessThan(1));
    }

    @Test
    public void testMismatchDescriptions() {
      assertMismatchDescription("<0> was less than <1>", greaterThan(1), 0);
      assertMismatchDescription("<1> was equal to <1>", greaterThan(1), 1);
      assertMismatchDescription("<1> was greater than <0>", lessThan(0), 1);
      assertMismatchDescription("<2> was equal to <2>", lessThan(2), 2);
    }

    @Test
    public void testComparesObjectsForGreaterThan() {
        assertThat(2, greaterThan(1));
        assertThat(0, not(greaterThan(1)));
    }

    @Test
    public void testComparesObjectsForLessThan() {
        assertThat(2, lessThan(3));
        assertThat(0, lessThan(1));
    }

    @Test
    public void testComparesObjectsForEquality() {
      assertThat(3, comparesEqualTo(3));
      assertThat("aa", comparesEqualTo("aa"));
    }

    @Test
    public void testAllowsForInclusiveComparisons() {
        assertThat("less", 1, lessThanOrEqualTo(1));
        assertThat("greater", 1, greaterThanOrEqualTo(1));
    }

    @Test
    public void testSupportsDifferentTypesOfComparableObjects() {
        assertThat(1.1, greaterThan(1.0));
        assertThat("cc", greaterThan("bb"));
    }

    @Test
    public void testComparesBigDecimalsWithDifferentScalesCorrectlyForIssue20() {
      assertThat(new BigDecimal("10.0"), greaterThanOrEqualTo(new BigDecimal("10")));
      assertThat(new BigDecimal(10), greaterThanOrEqualTo(new BigDecimal("10.0")));
      assertThat(new BigDecimal("2"), comparesEqualTo(new BigDecimal("2.000")));
    }

    @Test
    public void testComparesCustomTypesWhoseCompareToReturnsValuesGreaterThatOne() {
        assertThat(new CustomInt(5), lessThan(new CustomInt(10)));
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
