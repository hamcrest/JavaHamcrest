package org.hamcrest.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsEqual.eq;
import static org.hamcrest.core.AllOf.allOf;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

@SuppressWarnings("unchecked")
public class AllOfTest extends AbstractMatcherTest {

	protected Matcher<?> createMatcher() {
        return allOf(eq("irrelevant"), eq("irrelevant"));
    }

    public void testEvaluatesToTheTheLogicalConjunctionOfTwoOtherMatchers() {
        assertThat("good", allOf(eq("good"), eq("good")));

        assertThat("good", not(allOf(eq("bad"), eq("good"))));
        assertThat("good", not(allOf(eq("good"), eq("bad"))));
        assertThat("good", not(allOf(eq("bad"), eq("bad"))));
    }

    public void testEvaluatesToTheTheLogicalConjunctionOfManyOtherMatchers() {
        assertThat("good", allOf(eq("good"), eq("good"), eq("good"), eq("good"), eq("good")));
        assertThat("good", not(allOf(eq("good"), eq("good"), eq("bad"), eq("good"), eq("good"))));
    }

}
