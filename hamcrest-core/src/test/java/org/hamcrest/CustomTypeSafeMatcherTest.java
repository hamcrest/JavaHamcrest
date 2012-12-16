package org.hamcrest;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;

import org.junit.Test;

public final class CustomTypeSafeMatcherTest {
    private static final String STATIC_DESCRIPTION = "I match non empty strings";

    private final Matcher<String> customMatcher = new CustomTypeSafeMatcher<String>(STATIC_DESCRIPTION) {
        @Override
        public boolean matchesSafely(String item) {
            return false;
        }

        @Override
        public void describeMismatchSafely(String item, Description mismatchDescription) {
            mismatchDescription.appendText("an " + item);
        }
    };

    @Test public void
    usesStaticDescription() throws Exception {
        assertDescription(STATIC_DESCRIPTION, customMatcher);
    }

    @Test public void
    reportsMismatch() {
        assertMismatchDescription("an item", customMatcher, "item");
    }

    @Test public void
    isNullSafe() {
        assertNullSafe(customMatcher);
    }
    
    @Test public void
    copesWithUnknownTypes() {
        assertUnknownTypeSafe(customMatcher);
    }
}
