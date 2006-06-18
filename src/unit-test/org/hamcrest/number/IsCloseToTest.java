/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.number.IsCloseTo.eq;

public class IsCloseToTest extends AbstractMatcherTest {

    public void testEvaluatesToTrueIfArgumentIsEqualToADoubleValueWithinSomeError() {
        Matcher<Double> p = eq(1.0, 0.5);

        assertTrue(p.match(1.0));
        assertTrue(p.match(0.5d));
        assertTrue(p.match(1.5d));

        assertTrue("number too large", !p.match(2.0));
        assertTrue("number too small", !p.match(0.0));
    }

}
