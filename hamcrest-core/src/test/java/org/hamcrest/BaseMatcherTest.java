package org.hamcrest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class BaseMatcherTest {

    @Test
    public void
    describesItselfWithToStringMethod() {
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
