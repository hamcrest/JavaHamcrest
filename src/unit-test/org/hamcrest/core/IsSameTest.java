/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.core.IsSame.same;
import static org.hamcrest.core.IsNot.not;
import org.hamcrest.AbstractMatcherTest;
import static org.hamcrest.MatcherAssert.assertThat;


public class IsSameTest extends AbstractMatcherTest {
    public void testEvaluatesToTrueIfArgumentIsReferenceToASpecifiedObject() {
        Object o1 = new Object();
        Object o2 = new Object();

        assertThat(o1, same(o1));
        assertThat(o2, not(same(o1)));
    }

    public void testReturnsReadableDescriptionFromToString() {
        assertDescription("same(\"ARG\")", same("ARG"));
    }

    public void testReturnsReadableDescriptionFromToStringWhenInitialisedWithNull() {
        assertDescription("same(null)", same(null));
    }
}
