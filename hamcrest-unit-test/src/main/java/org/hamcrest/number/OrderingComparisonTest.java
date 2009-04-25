/*  Copyright (c) 2000-2009 hamcrest.org
 */
package org.hamcrest.number;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;

import java.math.BigDecimal;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class OrderingComparisonTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return greaterThan(1);
    }

    public void testDescription() {
      assertDescription("a value greater than <1>", greaterThan(1));
      assertDescription("a value equal to or greater than <1>", greaterThanOrEqualTo(1));
      assertDescription("a value equal to <1>", comparesEqualTo(1));
      assertDescription("a value less than or equal to <1>", lessThanOrEqualTo(1));
      assertDescription("a value less than <1>", lessThan(1));
    }

    public void testMismatchDescriptions() {
      assertMismatchDescription("<1> was less than <0>", greaterThan(1), 0);
      assertMismatchDescription("<1> was equal to <1>", greaterThan(1), 1);
      assertMismatchDescription("<0> was greater than <1>", lessThan(0), 1);
    }
    
    public void testComparesObjectsForGreaterThan() {
        assertThat(2, greaterThan(1));
        assertThat(0, not(greaterThan(1)));
    }

    public void testComparesObjectsForLessThan() {
        assertThat(2, lessThan(3));
        assertThat(0, lessThan(1));
    }


    public void testComparesObjectsForEquality() {
      assertThat(3, comparesEqualTo(3));
      assertThat("aa", comparesEqualTo("aa"));
    }

    public void testAllowsForInclusiveComparisons() {
        assertThat("less", 1, lessThanOrEqualTo(1));
        assertThat("greater", 1, greaterThanOrEqualTo(1));
    }

    public void testSupportsDifferentTypesOfComparableObjects() {
        assertThat(1.1, greaterThan(1.0));
        assertThat("cc", greaterThan("bb"));
    }
    
    public void testComparesBigDecimalsWithDifferentScalesCorrectlyForIssue20() {
      assertThat(new BigDecimal("10.0"), greaterThanOrEqualTo(new BigDecimal("10")));
      assertThat(new BigDecimal(10), greaterThanOrEqualTo(new BigDecimal("10.0")));
      assertThat(new BigDecimal("2"), comparesEqualTo(new BigDecimal("2.000")));
    }
}
