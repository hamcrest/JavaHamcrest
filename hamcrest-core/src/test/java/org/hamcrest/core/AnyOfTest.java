/*  Copyright (c) 2000-2010 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertEquals;

import org.hamcrest.Matcher;
import org.junit.Test;

public final class AnyOfTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = anyOf(equalTo("irrelevant"), startsWith("irr"));
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
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

    @Test public void
    hasAMismatchDescriptionDescribingTheFailingMatches() {
        assertMismatchDescription("was \"other\"", anyOf(equalTo("good"), equalTo("bad"), equalTo("ugly")), "other");
        assertMismatchDescription("was \"other\" and \"other\" was a java.lang.String", anyOf(equalTo("good"), instanceOf(Number.class)), "other");
    }

    @SuppressWarnings("unchecked")
    @Test public void
    hasCorrectParameterType() {
        final Matcher<SampleSubClass> matcher = anyOf(
            equalTo(new SampleBaseClass("bad")),
            is(nullValue()),
            equalTo(new SampleBaseClass("good")),
            equalTo(new SampleSubClass("ugly")));
        assertEquals(SampleSubClass.class, matcher.getParameterType());
    }
}
