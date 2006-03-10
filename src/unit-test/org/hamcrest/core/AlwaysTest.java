/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class AlwaysTest extends AbstractMatcherTest {

    public void testAlwaysReturnsFixedBooleanValueFromMatchesMethod() {
        Matcher matcher = new Always(true);
        assertTrue(matcher.match("something"));
        assertTrue(matcher.match("something else"));
        assertTrue(matcher.match(null));
        assertTrue(matcher.match(1));
        assertTrue(matcher.match(1.0));
        assertTrue(matcher.match(new Object()));

        matcher = new Always(false);
        assertFalse(matcher.match("something"));
        assertFalse(matcher.match("something else"));
        assertFalse(matcher.match(null));
        assertFalse(matcher.match(1));
        assertFalse(matcher.match(1.0));
        assertFalse(matcher.match(new Object()));
    }

    public void testIsGivenADescription() {
        String description = "DESCRIPTION";
        boolean irrelevantFlag = false;

        assertDescription(description, new Always(irrelevantFlag, description));
    }

}
