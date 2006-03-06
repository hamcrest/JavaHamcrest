/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;


public class IsNothingTest extends AbstractMatcherTest {
    public void testAlwaysEvaluatesToFalse() {
        IsNothing isNothing = new IsNothing();

        assertFalse(isNothing.match(null));
        assertFalse(isNothing.match(new Object()));
    }

    public void testHasUsefulDefaultDescription() {
        IsNothing isNothing = new IsNothing();

        assertDescription("NOTHING", isNothing);
    }

    public void testCanOverrideDescription() {
        String description = "description";
        IsNothing isNothing = new IsNothing(description);

        assertDescription(description, isNothing);
    }
}
