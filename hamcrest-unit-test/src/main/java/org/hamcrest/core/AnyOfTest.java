package org.hamcrest.core;

import java.util.Iterator;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.introspection.Combination;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

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

    public void testSupportsMixedTypes() {
        assertThat(new SampleBaseClass("good"), anyOf(
                equalTo(new SampleBaseClass("bad")),
                equalTo(new SampleBaseClass("good")),
                equalTo(new SampleSubClass("ugly"))
        ));
        assertThat(new SampleSubClass("good"), anyOf(
                equalTo(new SampleBaseClass("bad")),
                equalTo(new SampleBaseClass("good")),
                equalTo(new SampleSubClass("ugly"))
        ));
    }
    
    public void testCanIntrospectOnTheCombinedMatchers() {
    	Matcher<String> m1 = equalTo("good");
    	Matcher<String> m2 = equalTo("bad");
    	Matcher<String> m3 = equalTo("ugly");
    	
    	Matcher<String> any = anyOf(m1, m2, m3);
    	
    	Iterator<? extends Matcher<?>> iterator = ((Combination)any).combined().iterator();
    	assertSame(m1, iterator.next());
    	assertSame(m2, iterator.next());
    	assertSame(m3, iterator.next());
    	assertFalse(iterator.hasNext());
    }
}
