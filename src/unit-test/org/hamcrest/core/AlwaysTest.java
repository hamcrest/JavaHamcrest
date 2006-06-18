/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Always.alwaysFails;
import static org.hamcrest.core.Always.alwaysPasses;
import static org.hamcrest.core.IsNot.not;

public class AlwaysTest extends AbstractMatcherTest {

    public void testAlwaysReturnsFixedBooleanValueFromMatchesMethod() {
        assertThat("something", alwaysPasses());
        assertThat("something else", alwaysPasses());
        assertThat(null, alwaysPasses());
        assertThat(1, alwaysPasses());
        assertThat(1.0, alwaysPasses());
        assertThat(new Object(), alwaysPasses());

        assertThat("something", not(alwaysFails()));
        assertThat("something else", not(alwaysFails()));
        assertThat(null, not(alwaysFails()));
        assertThat(1, not(alwaysFails()));
        assertThat(1.0, not(alwaysFails()));
        assertThat(new Object(), not(alwaysFails()));
    }

    public void testIsGivenADescription() {
        String description = "DESCRIPTION";

        assertDescription("always passes", alwaysPasses());
        assertDescription("always fails", alwaysFails());
        assertDescription(description, alwaysPasses(description));
        assertDescription(description, alwaysFails(description));
    }

}
