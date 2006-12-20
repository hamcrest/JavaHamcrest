package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

@SuppressWarnings("unchecked")
public class AnyOfTest extends AbstractMatcherTest {

	protected Matcher<?> createMatcher() {
        return anyOf(equalTo("irrelevant"));
    }

    public void testEvaluatesToTheTheLogicalDisjunctionOfTwoOtherMatchers() {
        assertThat("good", anyOf(equalTo("bad"), equalTo("good")));
        assertThat("good", anyOf(equalTo("good"), equalTo("good")));
        assertThat("good", anyOf(equalTo("good"), equalTo("bad")));

        assertThat("good", not(anyOf(equalTo("bad"), equalTo("bad"))));
    }

    public void testEvaluatesToTheTheLogicalDisjunctionOfManyOtherMatchers() {
        assertThat("good", anyOf(equalTo("bad"), equalTo("good"), equalTo("bad"), equalTo("bad"), equalTo("bad")));
        assertThat("good", not(anyOf(equalTo("bad"), equalTo("bad"), equalTo("bad"), equalTo("bad"), equalTo("bad"))));
    }

}
