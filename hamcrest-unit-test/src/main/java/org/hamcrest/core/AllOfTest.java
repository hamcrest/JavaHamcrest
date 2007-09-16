package org.hamcrest.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import java.util.Iterator;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.introspection.Combination;

public class AllOfTest extends AbstractMatcherTest {
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
    
    public void testSupportsMixedTypes() {
        final Matcher<SampleSubClass> all = allOf(
                equalTo(new SampleBaseClass("bad")),
                equalTo(new SampleBaseClass("good")),
                equalTo(new SampleSubClass("ugly")));
        final Matcher<SampleSubClass> negated = not(all);
        
        assertThat(new SampleSubClass("good"), negated);
    }
    
    public void testCanIntrospectOnTheCombinedMatchers() {
    	Matcher<String> m1 = equalTo("good");
    	Matcher<String> m2 = equalTo("bad");
    	Matcher<String> m3 = equalTo("ugly");
    	
    	Matcher<String> all = allOf(m1, m2, m3);
    	
    	Iterator<? extends Matcher<?>> iterator = ((Combination)all).combined().iterator();
    	assertSame(m1, iterator.next());
    	assertSame(m2, iterator.next());
    	assertSame(m3, iterator.next());
    	assertFalse(iterator.hasNext());
    }

    protected Matcher<?> createMatcher() {
        return allOf(equalTo("irrelevant"), equalTo("irrelevant"));
    }
}
