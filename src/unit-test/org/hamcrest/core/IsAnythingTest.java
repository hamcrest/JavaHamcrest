/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import static org.hamcrest.core.IsAnything.anything;

public class IsAnythingTest extends AbstractMatcherTest {
    public void testAlwaysEvaluatesToTrue() {
        assertMatches("should have matched null", anything(), null);
        assertMatches("should have matched new Object()", anything(), new Object());
        assertMatches("should have matched string", anything(), "hi");
    }

    public void testHasUsefulDefaultDescription() {
        assertDescription("ANYTHING", anything());
    }

    public void testCanOverrideDescription() {
        String description = "description";
        assertDescription(description, anything(description));
    }
}
