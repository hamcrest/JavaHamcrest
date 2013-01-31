/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.number.IsCloseTo.closeTo;

public class IsCloseToTest extends AbstractMatcherTest {
  private final Matcher<Double> matcher = closeTo(1.0d, 0.5d);

  @Override
    protected Matcher<?> createMatcher() {
        final double irrelevant = 0.1;
        return closeTo(irrelevant, irrelevant);
    }

    public void test_matchesIfArgumentIsEqualToADoubleValueWithinSomeError() {
        assertMatches("1.0", matcher, 1.0);
        assertMatches("0.5d", matcher, 0.5d);
        assertMatches("1.5d", matcher, 1.5d);

        assertDoesNotMatch("too large", matcher, 2.0);
        assertMismatchDescription("<3.0> differed from <1.0> by <2.0>", matcher, 3.0d);
        assertDoesNotMatch("number too small", matcher, 0.0);
        assertMismatchDescription("<0.1> differed from <1.0> by <0.9>", matcher, 0.1);
    }

    public void test_is_self_describing() {
        assertDescription("a numeric value within <0.5> of <1.0>", matcher);
    }

}
