/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import static org.hamcrest.core.And.and;
import static org.hamcrest.core.IsEqual.eq;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class AndTest extends AbstractMatcherTest {

    public void testEvaluatesToTheTheLogicalConjunctionOfTwoOtherMatchers() {
        assertThat("good", and(eq("good"), eq("good")));

        assertThat("good", not(and(eq("bad"), eq("good"))));
        assertThat("good", not(and(eq("good"), eq("bad"))));
        assertThat("good", not(and(eq("bad"), eq("bad"))));
    }

    public void testEvaluatesToTheTheLogicalConjunctionOfManyOtherMatchers() {
        assertThat("good", and(eq("good"), eq("good"), eq("good"), eq("good"), eq("good")));
        assertThat("good", not(and(eq("good"), eq("good"), eq("bad"), eq("good"), eq("good"))));
    }

}
