/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;


public class IsInstanceOfTest extends AbstractMatcherTest {
    public void testEvaluatesToTrueIfArgumentIsInstanceOfASpecificClass() {
        IsInstanceOf isInstanceOf = new IsInstanceOf(Number.class);

        assertTrue(isInstanceOf.match(new Integer(1)));
        assertTrue(isInstanceOf.match(new Double(1.0)));
        assertFalse(isInstanceOf.match("a string"));
        assertFalse(isInstanceOf.match(null));
    }
}
