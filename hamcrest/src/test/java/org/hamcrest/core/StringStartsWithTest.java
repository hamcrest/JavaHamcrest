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

import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.hamcrest.core.StringStartsWith.startsWithIgnoringCase;


public class StringStartsWithTest extends AbstractMatcherTest {
    static final String EXCERPT = "EXCERPT";
    final Matcher<String> stringStartsWith = startsWith(EXCERPT);

    @Override
    protected Matcher<?> createMatcher() {
        return stringStartsWith;
    }

    public void testMatchesStringAtStart() {
        assertMatches(stringStartsWith, EXCERPT + "END");
        assertDoesNotMatch(stringStartsWith, "START" + EXCERPT);
        assertDoesNotMatch(stringStartsWith, "START" + EXCERPT + "END");
        assertMatches(stringStartsWith, EXCERPT);
        assertDoesNotMatch(stringStartsWith, EXCERPT.toLowerCase());
        assertMatches(stringStartsWith, EXCERPT + EXCERPT);
        assertDoesNotMatch(stringStartsWith, "EXCER");

        assertDescription("a string starting with \"EXCERPT\"", stringStartsWith);
        assertMismatchDescription("was \"Something else\"", stringStartsWith, "Something else");
    }

    public void testMatchesStringAtStartIgnoringCase() {
        final Matcher<String> ignoreCase = startsWithIgnoringCase("EXCerPT");

        assertMatches(ignoreCase, "exCerPT" + "END");
        assertDoesNotMatch(ignoreCase, "START" + "EXCerpt");
        assertDoesNotMatch(ignoreCase, "START" + "EXcerpT" + "END");
        assertMatches(ignoreCase, "excERPT");
        assertMatches(ignoreCase, "ExcerPT" + "EXCerpt");
        assertDoesNotMatch(ignoreCase, "ExcER");

        assertDescription("a string starting with \"EXCerPT\" ignoring case", ignoreCase);
        assertMismatchDescription("was \"Something else\"", ignoreCase, "Something else");
    }

}
