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

    public void testProvidesConvenientShortcutForNotEqualTo() {
        assertMatches("should match", not("A"), "B");
        assertMatches("should match", not("B"), "A");
        assertDoesNotMatch("should not match", not("A"), "A");
        assertDoesNotMatch("should not match", not("B"), "B");
        assertDescription("not \"A\"", not("A"));
    }

}
