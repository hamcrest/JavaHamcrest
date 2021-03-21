/**
 * BSD License
 *
 * Copyright (c) 2000-2021 www.hamcrest.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * Neither the name of Hamcrest nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hamcrest.number;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.number.OrderingComparison.*;

public class OrderingComparisonTest extends AbstractMatcherTest {

    @Override
    protected Matcher<Integer> createMatcher() {
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
      assertMismatchDescription("<0> was less than <1>", greaterThan(1), 0);
      assertMismatchDescription("<1> was equal to <1>", greaterThan(1), 1);
      assertMismatchDescription("<1> was greater than <0>", lessThan(0), 1);
      assertMismatchDescription("<2> was equal to <2>", lessThan(2), 2);
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
