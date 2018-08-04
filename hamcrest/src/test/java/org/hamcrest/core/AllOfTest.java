/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringStartsWith.startsWith;

public final class AllOfTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = allOf(equalTo("irrelevant"), startsWith("irr"));
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }
    
    @Test public void
    evaluatesToTheTheLogicalConjunctionOfTwoOtherMatchers() {
        Matcher<String> matcher = allOf(startsWith("goo"), endsWith("ood"));
        
        assertMatches("didn't pass both sub-matchers", matcher, "good");
        assertDoesNotMatch("didn't fail first sub-matcher", matcher, "mood");
        assertDoesNotMatch("didn't fail second sub-matcher", matcher, "goon");
        assertDoesNotMatch("didn't fail both sub-matchers", matcher, "fred");
    }

    @Test public void
    evaluatesToTheTheLogicalConjunctionOfManyOtherMatchers() {
        Matcher<String> matcher = allOf(startsWith("g"), startsWith("go"), endsWith("d"), startsWith("go"), startsWith("goo"));
        
        assertMatches("didn't pass all sub-matchers", matcher, "good");
        assertDoesNotMatch("didn't fail middle sub-matcher", matcher, "goon");
    }
    
    @Test public void
    supportsMixedTypes() {
        final Matcher<SampleSubClass> matcher = allOf(
                equalTo(new SampleBaseClass("bad")),
                is(notNullValue()),
                equalTo(new SampleBaseClass("good")),
                equalTo(new SampleSubClass("ugly")));
        
        assertDoesNotMatch("didn't fail last sub-matcher", matcher, new SampleSubClass("good"));
    }
    
    @Test public void
    hasAReadableDescription() {
        assertDescription("(\"good\" and \"bad\" and \"ugly\")",
                allOf(equalTo("good"), equalTo("bad"), equalTo("ugly")));
    }

    @Test public void
    hasAMismatchDescriptionDescribingTheFirstFailingMatch() {
        assertMismatchDescription("\"good\" was \"bad\"", allOf(equalTo("bad"), equalTo("good")), "bad");
    }

    @Test public void
    varargs(){
        assertThat("the text!", new AllOf<>(startsWith("the"), containsString("text"), endsWith("!")));
    }
}
