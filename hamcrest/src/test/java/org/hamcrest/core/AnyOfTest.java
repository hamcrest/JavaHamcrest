/**
 * BSD License
 *
 * Copyright (c) 2000-2021 www.hamcrest.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * Neither the name of Hamcrest nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringStartsWith.startsWith;

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
    varargs(){
        assertThat("the text!", new AnyOf<>(startsWith("the"), endsWith(".")));
    }
}
