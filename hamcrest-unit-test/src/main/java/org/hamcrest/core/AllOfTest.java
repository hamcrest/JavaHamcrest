package org.hamcrest.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class AllOfTest extends AbstractMatcherTest {

    @SuppressWarnings("unchecked")
    protected Matcher<?> createMatcher() {
        return allOf(equalTo("irrelevant"));
    }
    
    @SuppressWarnings("unchecked")
    public void testEvaluatesToTheTheLogicalConjunctionOfTwoOtherMatchers() {
        assertThat("good", allOf(equalTo("good"), equalTo("good")));

        assertThat("good", not(allOf(equalTo("bad"), equalTo("good"))));
        assertThat("good", not(allOf(equalTo("good"), equalTo("bad"))));
        assertThat("good", not(allOf(equalTo("bad"), equalTo("bad"))));
    }

    @SuppressWarnings("unchecked")
    public void testEvaluatesToTheTheLogicalConjunctionOfManyOtherMatchers() {
        assertThat("good", allOf(equalTo("good"), equalTo("good"), equalTo("good"), equalTo("good"), equalTo("good")));
        assertThat("good", not(allOf(equalTo("good"), equalTo("good"), equalTo("bad"), equalTo("good"), equalTo("good"))));
    }
    
    @SuppressWarnings("unchecked")
    public void testSupportsMixedTypes() {
        final Matcher<SampleSubClass> all = allOf(
                equalTo(new SampleBaseClass("bad")),
                equalTo(new SampleBaseClass("good")),
                equalTo(new SampleSubClass("ugly")));
        final Matcher<SampleSubClass> negated = not(all);
        
        assertThat(new SampleSubClass("good"), negated);
    }
    
    public void testHasAReadableDescription() {
        assertDescription("(\"good\" and \"bad\" and \"ugly\")",
                allOf(equalTo("good"), equalTo("bad"), equalTo("ugly")));
    }
}
