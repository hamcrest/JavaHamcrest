/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.core.IsNot.not;

public class IsNotTest extends AbstractMatcherTest {

    protected Matcher<?> createMatcher() {
        return not(FALSE_MATCHER);
    }

	public void testEvaluatesToTheTheLogicalNegationOfAnotherMatcher() {
        assertFalse(not(TRUE_MATCHER).matches(ARGUMENT_IGNORED));
        assertTrue(not(FALSE_MATCHER).matches(ARGUMENT_IGNORED));
    }
}
