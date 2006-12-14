package org.hamcrest.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.AllOf.allOf;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

@SuppressWarnings("unchecked")
public class AllOfTest extends AbstractMatcherTest {

	protected Matcher<?> createMatcher() {
        return allOf(equalTo("irrelevant"), equalTo("irrelevant"));
    }

    public void testEvaluatesToTheTheLogicalConjunctionOfTwoOtherMatchers() {
        assertThat("good", allOf(equalTo("good"), equalTo("good")));

        assertThat("good", not(allOf(equalTo("bad"), equalTo("good"))));
        assertThat("good", not(allOf(equalTo("good"), equalTo("bad"))));
        assertThat("good", not(allOf(equalTo("bad"), equalTo("bad"))));
    }

    public void testEvaluatesToTheTheLogicalConjunctionOfManyOtherMatchers() {
        assertThat("good", allOf(equalTo("good"), equalTo("good"), equalTo("good"), equalTo("good"), equalTo("good")));
        assertThat("good", not(allOf(equalTo("good"), equalTo("good"), equalTo("bad"), equalTo("good"), equalTo("good"))));
    }

}
