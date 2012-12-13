/*  Copyright (c) 2000-2010 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class AllOfTest extends AbstractMatcherTest {

    @Override
    @SuppressWarnings("unchecked")
    protected Matcher<?> createMatcher() {
        return allOf(IsEqual.<Object>equalTo("irrelevant"));
    }
    
    public void testEvaluatesToTheTheLogicalConjunctionOfTwoOtherMatchers() {
        assertThat("good", allOf(equalTo("good"), startsWith("good")));

        assertThat("good", not(allOf(equalTo("bad"), equalTo("good"))));
        assertThat("good", not(allOf(equalTo("good"), equalTo("bad"))));
        assertThat("good", not(allOf(equalTo("bad"), equalTo("bad"))));
    }

    public void testEvaluatesToTheTheLogicalConjunctionOfManyOtherMatchers() {
        assertThat("good", allOf(equalTo("good"), equalTo("good"), equalTo("good"), equalTo("good"), equalTo("good")));
        assertThat("good", not(allOf(equalTo("good"), equalTo("good"), equalTo("bad"), equalTo("good"), equalTo("good"))));
    }
    
    public void testSupportsMixedTypes() {
        final Matcher<SampleSubClass> all = allOf(
                equalTo(new SampleBaseClass("bad")),
                is(notNullValue()),
                equalTo(new SampleBaseClass("good")),
                equalTo(new SampleSubClass("ugly")));
        final Matcher<SampleSubClass> negated = not(all);
        
        assertThat(new SampleSubClass("good"), negated);
    }
    
    public void testHasAReadableDescription() {
        assertDescription("(\"good\" and \"bad\" and \"ugly\")",
                allOf(equalTo("good"), equalTo("bad"), equalTo("ugly")));
    }

    public void testMismatchDescriptionDescribesFirstFailingMatch() {
        assertMismatchDescription("\"good\" was \"bad\"", allOf(equalTo("bad"), equalTo("good")), "bad");
    }
}
