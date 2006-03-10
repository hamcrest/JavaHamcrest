/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;


public class IsNotTest extends AbstractMatcherTest {
    public void testEvaluatesToTheTheLogicalNegationOfAnotherMatcher() {
        assertFalse(new IsNot(TRUE_MATCHER).match(ARGUMENT_IGNORED));
        assertTrue(new IsNot(FALSE_MATCHER).match(ARGUMENT_IGNORED));
    }
}
