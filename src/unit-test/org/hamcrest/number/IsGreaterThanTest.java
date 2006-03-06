/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.AbstractMatcherTest;


public class IsGreaterThanTest extends AbstractMatcherTest {
    public void testEvaluatesToTrueIfArgumentIsGreaterThanAComparableObject() {
        IsGreaterThan c = new IsGreaterThan(new Integer(1));

        assertFalse(c.match(new Integer(0)));
        assertFalse(c.match(new Integer(1)));
        assertTrue(c.match(new Integer(2)));
    }
}
