/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.core.IsNot.not;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsAnything.any;

public class IsNullTest extends AbstractMatcherTest {

    protected Matcher<?> createMatcher() {
        return nullValue();
    }

    public void testEvaluatesToTrueIfArgumentIsNull() {
        assertThat(null, nullValue());
        assertThat(ANY_NON_NULL_ARGUMENT, not(nullValue()));

        assertThat(ANY_NON_NULL_ARGUMENT, notNullValue());
        assertThat(null, not(notNullValue()));
    }

    public void testSupportsStaticTyping() {
        requiresStringMatcher(nullValue(String.class));
        requiresStringMatcher(notNullValue(String.class));
    }

    private void requiresStringMatcher(Matcher<String> arg) {
        // no-op
    }    
}
