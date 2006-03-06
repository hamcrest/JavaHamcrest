/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;


public class IsNullTest extends AbstractMatcherTest {
    public void testEvaluatesToTrueIfArgumentIsNull() {
        IsNull isNull = new IsNull();

        assertTrue(isNull.match(null));
        assertFalse(isNull.match(ANY_NON_NULL_ARGUMENT));
    }
}
