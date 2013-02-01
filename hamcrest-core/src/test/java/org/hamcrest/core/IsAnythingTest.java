/*  Copyright (c) 2000-2010 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.core.IsAnything.anything;

import org.hamcrest.Matcher;
import org.junit.Test;

public final class IsAnythingTest {

    private final Matcher<Object> matcher = anything();

    private static class CustomThing { }
    
    @Test public void
    alwaysEvaluatesToTrue() {
        assertMatches("didn't match null", matcher, null);
        assertMatches("didn't match Object", matcher, new Object());
        assertMatches("didn't match custom object", matcher, new CustomThing());
        assertMatches("didn't match String", matcher, "hi");
    }

    @Test public void
    hasUsefulDefaultDescription() {
        assertDescription("anything", matcher);
    }

    @Test public void
    canOverrideDescription() {
        String description = "description";
        assertDescription(description, anything(description));
    }

}
