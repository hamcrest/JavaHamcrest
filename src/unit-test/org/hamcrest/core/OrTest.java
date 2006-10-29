/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.eq;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.Or.or;

@SuppressWarnings("unchecked")
public class OrTest extends AbstractMatcherTest {

	protected Matcher<?> createMatcher() {
        return or(eq("irrelevant"));
    }

    public void testEvaluatesToTheTheLogicalDisjunctionOfTwoOtherMatchers() {
        assertThat("good", or(eq("bad"), eq("good")));
        assertThat("good", or(eq("good"), eq("good")));
        assertThat("good", or(eq("good"), eq("bad")));

        assertThat("good", not(or(eq("bad"), eq("bad"))));
    }

    public void testEvaluatesToTheTheLogicalDisjunctionOfManyOtherMatchers() {
        assertThat("good", or(eq("bad"), eq("good"), eq("bad"), eq("bad"), eq("bad")));
        assertThat("good", not(or(eq("bad"), eq("bad"), eq("bad"), eq("bad"), eq("bad"))));
    }

}
