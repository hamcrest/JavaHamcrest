/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.number.IsCloseTo.closeTo;

public class IsCloseToTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        final double irrelevant = 0.1;
        return closeTo(irrelevant, irrelevant);
    }

    public void test_matchesIfArgumentIsEqualToADoubleValueWithinSomeError() {
        final Matcher<Double> p = closeTo(1.0d, 0.5d);

        assertMatches("1.0", p, 1.0);
        assertMatches("0.5d", p, 0.5d);
        assertMatches("1.5d", p, 1.5d);

        assertDoesNotMatch("too large", p, 2.0);
        assertMismatchDescription("<3.0> differed by <1.5> more than delta <0.5>", p, 3.0d);
        assertDoesNotMatch("number too small", p, 0.0);
        assertMismatchDescription("<0.1> differed by <0.4> more than delta <0.5>", p, 0.1);
    }
}
