/*  Copyright (c) 2000-20010 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

import java.math.BigDecimal;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;


public class IsNullTest extends AbstractMatcherTest {
    private static final BigDecimal ANY_NON_NULL_ARGUMENT = new BigDecimal(66);

    @Override
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

    private void requiresStringMatcher(@SuppressWarnings("unused") Matcher<String> arg) {
        // no-op
    }    
}
