/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsAnything.anything;

public final class IsAnythingTest {

    private final Matcher<Object> matcher = anything();

    private static class CustomThing {
    }

    @Test
    public void
    alwaysEvaluatesToTrue() {
        assertMatches("didn't match null", matcher, null);
        assertMatches("didn't match Object", matcher, new Object());
        assertMatches("didn't match custom object", matcher, new CustomThing());
        assertMatches("didn't match String", matcher, "hi");
    }

    @Test
    public void compilesWithoutTypeWarnings() {
        assertThat(new CustomThing(), is(anything()));
    }

    @Test public void
    hasUsefulDefaultDescription() {
        assertDescription("ANYTHING", matcher);
    }

    @Test public void
    canOverrideDescription() {
        String description = "description";
        assertDescription(description, anything(description));
    }

}
