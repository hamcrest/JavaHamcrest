package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringStartsWith.startsWith;

public final class AnyOfTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = anyOf(equalTo("irrelevant"), startsWith("irr"));
        
        assertNullSafe(matcher);
    }

    @Test public void
    evaluatesToTheTheLogicalDisjunctionOfTwoOtherMatchers() {
        Matcher<String> matcher = anyOf(startsWith("goo"), endsWith("ood"));
        
        assertMatches("didn't pass both sub-matchers", matcher, "good");
        assertMatches("didn't pass second sub-matcher", matcher, "mood");
        assertMatches("didn't pass first sub-matcher", matcher, "goon");
        assertDoesNotMatch("didn't fail both sub-matchers", matcher, "flan");
    }

    @Test public void
    evaluatesToTheTheLogicalDisjunctionOfManyOtherMatchers() {
        Matcher<String> matcher = anyOf(startsWith("g"), startsWith("go"), endsWith("d"), startsWith("go"), startsWith("goo"));
        
        assertMatches("didn't pass middle sub-matcher", matcher, "vlad");
        assertDoesNotMatch("didn't fail all sub-matchers", matcher, "flan");
    }

    @SuppressWarnings("unchecked")
    @Test public void
    supportsMixedTypes() {
        final Matcher<SampleSubClass> matcher = anyOf(
                equalTo(new SampleBaseClass("bad")),
                equalTo(new SampleBaseClass("good")),
                equalTo(new SampleSubClass("ugly")));
        
        assertMatches("didn't pass middle sub-matcher", matcher, new SampleSubClass("good"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("(\"good\" or \"bad\" or \"ugly\")",
                anyOf(equalTo("good"), equalTo("bad"), equalTo("ugly")));
    }
}
