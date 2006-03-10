/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;


public class IsSameTest extends AbstractMatcherTest {
    public void testEvaluatesToTrueIfArgumentIsReferenceToASpecifiedObject() {
        Object o1 = new Object();
        Object o2 = new Object();

        IsSame isSame = new IsSame(o1);

        assertTrue(isSame.match(o1));
        assertFalse(isSame.match(o2));
    }

    public void testReturnsReadableDescriptionFromToString() {
        IsSame isSame = new IsSame("ARG");
        assertDescription("same(\"ARG\")", isSame);
    }

    public void testReturnsReadableDescriptionFromToStringWhenInitialisedWithNull() {
        IsSame isSame = new IsSame(null);
        assertDescription("same(null)", isSame);
    }
}
