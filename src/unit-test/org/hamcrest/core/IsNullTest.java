/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.core.IsNot.not;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.isNull;
import static org.hamcrest.core.IsNull.isNotNull;

public class IsNullTest extends AbstractMatcherTest {

    protected Matcher<?> createMatcher() {
        return isNull();
    }

    public void testEvaluatesToTrueIfArgumentIsNull() {
        assertThat(null, isNull());
        assertThat(ANY_NON_NULL_ARGUMENT, not(isNull()));

        assertThat(ANY_NON_NULL_ARGUMENT, isNotNull());
        assertThat(null, not(isNotNull()));
    }
}
