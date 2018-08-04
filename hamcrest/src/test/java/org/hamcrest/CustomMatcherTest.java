/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest;

import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.assertDescription;

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
