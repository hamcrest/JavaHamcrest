/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;


public class IsAnythingTest extends AbstractMatcherTest {
    public void testAlwaysEvaluatesToTrue() {
        IsAnything isAnything = new IsAnything();

        assertTrue(isAnything.match(null));
        assertTrue(isAnything.match(new Object()));
    }

    public void testHasUsefulDefaultDescription() {
        IsAnything isAnything = new IsAnything();

        assertDescription("ANYTHING", isAnything);
    }

    public void testCanOverrideDescription() {
        String description = "description";
        IsAnything isAnything = new IsAnything(description);

        assertDescription(description, isAnything);
    }
}
