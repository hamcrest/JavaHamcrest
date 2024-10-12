package org.hamcrest;

import org.junit.jupiter.api.Test;

import static org.hamcrest.test.MatcherAssertions.assertDescription;


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
