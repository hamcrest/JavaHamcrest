package org.hamcrest;

import static org.hamcrest.AbstractMatcherTest.assertDescription;

import org.junit.Test;

public final class CustomMatcherTest {

    @Test public void
    usesStaticDescription() throws Exception {
        Matcher<String> matcher = new CustomMatcher<String>("I match strings") {
            @Override
            public boolean matches(Object item) {
                return (item instanceof String);
            }
        };

        assertDescription("I match strings", matcher);
    }
}
