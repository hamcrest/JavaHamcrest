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
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;

public final class CombinableTest {
    private static final CombinableMatcher<Integer> EITHER_3_OR_4 = CombinableMatcher.either(equalTo(3)).or(equalTo(4));
    private static final CombinableMatcher<Integer> NOT_3_AND_NOT_4 = CombinableMatcher.both(not(equalTo(3))).and(not(equalTo(4)));

    @Test public void
    copesWithNullsAndUnknownTypes() {
        assertNullSafe(EITHER_3_OR_4);
        assertNullSafe(NOT_3_AND_NOT_4);
        assertUnknownTypeSafe(EITHER_3_OR_4);
        assertUnknownTypeSafe(NOT_3_AND_NOT_4);
    }

    @Test public void
    bothAcceptsAndRejects() {
        assertMatches("both didn't pass", NOT_3_AND_NOT_4, 2);
        assertDoesNotMatch("both didn't fail", NOT_3_AND_NOT_4, 3);
    }

    @Test public void
    acceptsAndRejectsThreeAnds() {
        CombinableMatcher<? super Integer> tripleAnd = NOT_3_AND_NOT_4.and(equalTo(2));
        
        assertMatches("tripleAnd didn't pass", tripleAnd, 2);
        assertDoesNotMatch("tripleAnd didn't fail", tripleAnd, 3);
    }

    @Test public void
    bothDescribesItself() {
        assertDescription("(not <3> and not <4>)", NOT_3_AND_NOT_4);
        assertMismatchDescription("not <3> was <3>", NOT_3_AND_NOT_4, 3);
    }

    @Test public void
    eitherAcceptsAndRejects() {
        assertMatches("either didn't pass", EITHER_3_OR_4, 3);
        assertDoesNotMatch("either didn't fail", EITHER_3_OR_4, 6);
    }

    @Test public void
    acceptsAndRejectsThreeOrs() {
        final CombinableMatcher<Integer> tripleOr = EITHER_3_OR_4.or(equalTo(11));
        
        assertMatches("tripleOr didn't pass", tripleOr, 11);
        assertDoesNotMatch("tripleOr didn't fail", tripleOr, 9);
    }

    @Test public void
    eitherDescribesItself() {
        assertDescription("(<3> or <4>)", EITHER_3_OR_4);
        assertMismatchDescription("was <6>", EITHER_3_OR_4, 6);
    }

    @Test public void
    picksUpTypeFromLeftHandSideOfExpression() {
        @SuppressWarnings("unused")
        Matcher<String> matcher = CombinableMatcher.both(equalTo("yellow")).and(notNullValue(String.class));
    }
}
