/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.AbstractMatcherTest;


public class IsCloseToTest extends AbstractMatcherTest {

    public void testEvaluatesToTrueIfArgumentIsEqualToADoubleValueWithinSomeError() {
        IsCloseTo p = new IsCloseTo(1.0, 0.5);

        assertTrue(p.match(new Double(1.0)));
        assertTrue(p.match(new Double(0.5)));
        assertTrue(p.match(new Double(1.5)));

        assertTrue(p.match(new Float(1.0)));
        assertTrue(p.match(new Integer(1)));

        assertTrue("number too large", !p.match(new Double(2.0)));
        assertTrue("number too small", !p.match(new Double(0.0)));

        try {
            p.match("wrong type");
            fail("ClassCastException expected for wrong type of argument");
        }
        catch (ClassCastException ex) {
            // expected
        }
    }

}
