/*  Copyright (c) 2000-2010 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class AnyOfTest extends AbstractMatcherTest {

    @Override
    @SuppressWarnings("unchecked")
    protected Matcher<?> createMatcher() {
        return anyOf(IsEqual.<Object>equalTo("irrelevant"));
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

    @SuppressWarnings("unchecked")
    public void testSupportsMixedTypes() {
        final Matcher<SampleSubClass> combined = anyOf(
                equalTo(new SampleBaseClass("bad")),
                equalTo(new SampleBaseClass("good")),
                equalTo(new SampleSubClass("ugly"))
        );
        
        assertThat(new SampleSubClass("good"), combined);
    }    
    
    public void testHasAReadableDescription() {
        assertDescription("(\"good\" or \"bad\" or \"ugly\")",
                anyOf(equalTo("good"), equalTo("bad"), equalTo("ugly")));
    }
}
