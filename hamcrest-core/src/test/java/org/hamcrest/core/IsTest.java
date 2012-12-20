package org.hamcrest.core;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.Matcher;
import org.junit.Test;

public final class IsTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = is("something");
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    matchesTheSameWayTheUnderlyingMatcherDoes() {
        final Matcher<Boolean> matcher = is(equalTo(true));

        assertMatches("didn't match", matcher, true);
        assertDoesNotMatch("matched unexpectedly", matcher, false);
    }

    @Test public void
    generatesIsPrefixInDescription() {
        assertDescription("is <true>", is(equalTo(true)));
        assertDescription("is \"A\"", is("A"));
    }

    @Test public void
    providesConvenientShortcutForIsEqualTo() {
        final Matcher<String> matcher = is("A");
        
        assertMatches("didn't match", matcher, "A");
        assertDoesNotMatch("matched unexpectedly", is("A"), "B");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test public void
    providesConvenientShortcutForIsInstanceOf() {
        final Matcher matcher = isA(Integer.class);
        assertMatches("didn't match", matcher, Integer.valueOf(1));
        assertDoesNotMatch("matched unexpectedly", matcher, new Object());
        assertDoesNotMatch("matched unexpectedly", matcher, null);
    }
}
