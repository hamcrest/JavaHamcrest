/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import static org.hamcrest.core.IsNot.not;

public class IsNotTest extends AbstractMatcherTest {
    @SuppressWarnings("unchecked")
	public void testEvaluatesToTheTheLogicalNegationOfAnotherMatcher() {
        assertFalse(not(TRUE_MATCHER).match(ARGUMENT_IGNORED));
        assertTrue(not(FALSE_MATCHER).match(ARGUMENT_IGNORED));
    }
}
