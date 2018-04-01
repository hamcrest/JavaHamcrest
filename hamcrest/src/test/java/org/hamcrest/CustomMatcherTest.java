package org.hamcrest;

import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.assertDescription;

public final class CustomMatcherTest {

    @Test public void
    usesStaticDescription() throws Exception {
        Matcher<String> matcher = new CustomMatcher<String>("I match strings") {
            @Override
            public boolean matches(String item) {
                return (item instanceof String);
            }
        };

        assertDescription("I match strings", matcher);
    }
}
