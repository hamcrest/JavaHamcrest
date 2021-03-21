package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsEqual.equalTo;

public final class IsTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = is("something");
        
        assertNullSafe(matcher);
    }

    @Test public void
    matchesTheSameWayTheUnderlyingMatcherDoes() {
        final Matcher<Boolean> matcher = is(equalTo(true));

        assertMatches(matcher, true);
        assertDoesNotMatch(matcher, false);
    }

    @Test public void
    generatesIsPrefixInDescription() {
        assertDescription("is <true>", is(equalTo(true)));
        assertDescription("is \"A\"", is("A"));
    }

    @Test public void
    providesConvenientShortcutForIsEqualTo() {
        final Matcher<String> matcher = is("A");
        
        assertMatches(matcher, "A");
        assertDoesNotMatch(is("A"), "B");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test public void
    providesConvenientShortcutForIsInstanceOf() {
        final Matcher matcher = isA(Number.class);
        assertMatches(matcher, 1);
        assertDoesNotMatch(matcher, new Object());
        assertDoesNotMatch(matcher, null);
    }
}
