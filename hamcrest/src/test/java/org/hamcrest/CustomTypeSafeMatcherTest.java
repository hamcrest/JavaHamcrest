/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest;

import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;

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
