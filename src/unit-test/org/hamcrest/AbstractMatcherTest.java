/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.hamcrest.core.Always;
import org.hamcrest.internal.StringDescription;

public abstract class AbstractMatcherTest extends TestCase {
    protected static final Matcher TRUE_MATCHER = new Always(true);
    protected static final Matcher FALSE_MATCHER = new Always(false);

    protected static final Matcher NEVER_EVALUATED = new Matcher() {
        public boolean match(Object o) {
            throw new AssertionFailedError("matcher should not have been evaluated");
        }

        public void describeTo(Description description) {
            description.appendText("NEVER_EVALUATED");
        }
    };

    protected static final Object ARGUMENT_IGNORED = new Object();
    protected static final Object ANY_NON_NULL_ARGUMENT = new Object();

    public void assertMatches(String message, Matcher c, Object arg) {
        assertTrue(message, c.match(arg));
    }

    public void assertDoesNotMatch(String message, Matcher c, Object arg) {
        assertFalse(message, c.match(arg));
    }

    public void assertDescription(String expected, Matcher matcher) {
        Description description = new StringDescription();
        matcher.describeTo(description);
        assertEquals(expected, description.toString());
    }
}
