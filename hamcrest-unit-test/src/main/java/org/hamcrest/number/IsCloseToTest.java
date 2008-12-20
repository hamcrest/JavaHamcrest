/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class IsCloseToTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        double irrelevant = 0.1;
        return closeTo(irrelevant, irrelevant);
    }

    public void testEvaluatesToTrueIfArgumentIsEqualToADoubleValueWithinSomeError() {
        Matcher<Double> p = closeTo(1.0, 0.5);

        assertTrue(p.matches(1.0));
        assertTrue(p.matches(0.5d));
        assertTrue(p.matches(1.5d));

        assertDoesNotMatch("too large", p, 2.0);
        assertMismatchDescription("<2.0> differed by <0.5>", p, 2.0);
        assertDoesNotMatch("number too small", p, 0.0);
        assertMismatchDescription("<0.0> differed by <0.5>", p, 0.0);
    }

}
