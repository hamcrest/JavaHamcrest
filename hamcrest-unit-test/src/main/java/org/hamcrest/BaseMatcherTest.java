package org.hamcrest;

import junit.framework.TestCase;

public class BaseMatcherTest extends TestCase {

    public void testDescribesItselfWithToStringMethod() {
        Matcher someMatcher = new BaseMatcher() {
            public boolean matches(Object item) {
                throw new UnsupportedOperationException();
            }

            public void describeTo(Description description) {
                description.appendText("SOME DESCRIPTION");
            }
        };

        assertEquals("SOME DESCRIPTION", someMatcher.toString());
    }
}
