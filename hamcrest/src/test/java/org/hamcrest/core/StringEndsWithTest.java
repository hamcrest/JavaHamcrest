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

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringEndsWith.endsWithIgnoringCase;


public class StringEndsWithTest extends AbstractMatcherTest {
    static final String EXCERPT = "EXCERPT";
    final Matcher<String> stringEndsWith = endsWith(EXCERPT);

    @Override
    protected Matcher<?> createMatcher() {
        return stringEndsWith;
    }

    public void testMatchesSubstringAtEnd() {
        assertDoesNotMatch(stringEndsWith, EXCERPT + "END");
        assertMatches(stringEndsWith, "START" + EXCERPT);
        assertMatches(stringEndsWith, EXCERPT);
        assertDoesNotMatch(stringEndsWith, EXCERPT.toLowerCase());
        assertDoesNotMatch(stringEndsWith, "START" + EXCERPT + "END");
        assertMatches(stringEndsWith, EXCERPT + EXCERPT);
        assertDoesNotMatch(stringEndsWith, "EXCER");

        assertMismatchDescription("was \"Something else\"", stringEndsWith, "Something else");
        assertDescription("a string ending with \"EXCERPT\"", stringEndsWith);
    }

    public void testMatchesSubstringAtEndIngoringCase() {
        final Matcher<String> ignoringCase = endsWithIgnoringCase("EXCERpt");
        assertDoesNotMatch(ignoringCase, "eXCErpt" + "END");
        assertMatches(ignoringCase, "START" + "EXceRpt");
        assertMatches(ignoringCase, "EXcerPT");
        assertDoesNotMatch(ignoringCase, "START" + "ExcERpt" + "END");
        assertMatches(ignoringCase, "exCERpt" + "EXCerPt");
        assertDoesNotMatch(ignoringCase, "ExcER");

        assertMismatchDescription("was \"Something else\"", ignoringCase, "Something else");
        assertDescription("a string ending with \"EXCERpt\" ignoring case", ignoringCase);
    }

}
