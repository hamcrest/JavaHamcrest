/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;


public class AndTest extends AbstractMatcherTest {
    public void testEvaluatesToTheTheLogicalConjunctionOfTwoOtherMatchers() {
        assertTrue(new And(TRUE_MATCHER, TRUE_MATCHER).match(ARGUMENT_IGNORED));
        assertFalse(new And(FALSE_MATCHER, TRUE_MATCHER).match(ARGUMENT_IGNORED));
        assertFalse(new And(TRUE_MATCHER, FALSE_MATCHER).match(ARGUMENT_IGNORED));
        assertFalse(new And(FALSE_MATCHER, FALSE_MATCHER).match(ARGUMENT_IGNORED));
    }

    public void testEvaluatesArgumentsLeftToRightAndShortCircuitsEvaluation() {
        assertFalse(new And(FALSE_MATCHER, NEVER_EVALUATED).match(ARGUMENT_IGNORED));
    }
}
