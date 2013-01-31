/*  Copyright (c) 2000-2009 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

import org.hamcrest.Matcher;
import org.junit.Test;

public final class IsNotTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = not("something");

        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    evaluatesToTheTheLogicalNegationOfAnotherMatcher() {
        final Matcher<String> matcher = not(equalTo("A"));

        assertMatches("didn't match", matcher, "B");
        assertDoesNotMatch("matched unexpectedly", matcher, "A");
    }

    @Test public void
    providesConvenientShortcutForNotEqualTo() {
        final Matcher<String> matcher = not("A");

        assertMatches("didn't match", matcher, "B");
        assertDoesNotMatch("matched unexpectedly", matcher, "A");
    }

    @Test public void
    usesDescriptionOfNegatedMatcherWithPrefix() {
        assertDescription("not an instance of java.lang.String", not(instanceOf(String.class)));
        assertDescription("not \"A\"", not("A"));
    }

    @Test public void
    hasCorrectParameterType() {
        assertEquals(Number.class, not(any(Number.class)).getParameterType());
    }
}
