package org.hamcrest;

import junit.framework.TestCase;

public class BaseMatcherTest extends TestCase {

    public void testDescribesItselfWithToStringMethod() {
        Matcher<Object> someMatcher = new BaseMatcher<Object>() {
            @Override
            public boolean matches(Object item) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("SOME DESCRIPTION");
            }
        };

        assertEquals("SOME DESCRIPTION", someMatcher.toString());
    }
}
