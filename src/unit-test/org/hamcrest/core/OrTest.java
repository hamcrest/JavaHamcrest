/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;


public class OrTest extends AbstractMatcherTest {
    public void testEvaluatesToTheTheLogicalDisjunctionOfTwoOtherMatcher() {
        assertTrue(new Or(TRUE_MATCHER, TRUE_MATCHER).match(ARGUMENT_IGNORED));
        assertTrue(new Or(FALSE_MATCHER, TRUE_MATCHER).match(ARGUMENT_IGNORED));
        assertTrue(new Or(TRUE_MATCHER, FALSE_MATCHER).match(ARGUMENT_IGNORED));
        assertFalse(new Or(FALSE_MATCHER, FALSE_MATCHER).match(ARGUMENT_IGNORED));
    }


    public void testEvaluatesArgumentsLeftToRightAndShortCircuitsEvaluation() {
        assertTrue(new Or(TRUE_MATCHER, NEVER_EVALUATED).match(ARGUMENT_IGNORED));
    }
}
