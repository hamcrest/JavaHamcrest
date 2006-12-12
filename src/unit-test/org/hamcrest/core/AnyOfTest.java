package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.eq;
import static org.hamcrest.core.IsNot.not;

@SuppressWarnings("unchecked")
public class AnyOfTest extends AbstractMatcherTest {

	protected Matcher<?> createMatcher() {
        return anyOf(eq("irrelevant"));
    }

    public void testEvaluatesToTheTheLogicalDisjunctionOfTwoOtherMatchers() {
        assertThat("good", anyOf(eq("bad"), eq("good")));
        assertThat("good", anyOf(eq("good"), eq("good")));
        assertThat("good", anyOf(eq("good"), eq("bad")));

        assertThat("good", not(anyOf(eq("bad"), eq("bad"))));
    }

    public void testEvaluatesToTheTheLogicalDisjunctionOfManyOtherMatchers() {
        assertThat("good", anyOf(eq("bad"), eq("good"), eq("bad"), eq("bad"), eq("bad")));
        assertThat("good", not(anyOf(eq("bad"), eq("bad"), eq("bad"), eq("bad"), eq("bad"))));
    }

}
