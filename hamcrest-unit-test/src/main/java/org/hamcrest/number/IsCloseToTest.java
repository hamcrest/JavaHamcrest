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

        assertTrue("number too large", !p.matches(2.0));
        assertTrue("number too small", !p.matches(0.0));
    }

}
