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
package org.hamcrest.object;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

@SuppressWarnings("WeakerAccess")
public class HasEqualsValuesTest extends AbstractMatcherTest {
    private static final WithPublicFields WITH_PUBLIC_FIELDS = new WithPublicFields('x', 666, "a string");
    private static final HasEqualValues<WithPublicFields> WITH_PUBLIC_FIELDS_MATCHER = new HasEqualValues<>(WITH_PUBLIC_FIELDS);

    @Override
    protected Matcher<?> createMatcher() {
        return WITH_PUBLIC_FIELDS_MATCHER;
    }

    public void test_describes_itself() {
        assertDescription(
                "WithPublicFields has values [i: <666>, s: \"a string\", c: \"x\"]",
                WITH_PUBLIC_FIELDS_MATCHER);
    }

    public void test_matches_equivalent_object() {
        assertMatches(WITH_PUBLIC_FIELDS_MATCHER, new WithPublicFields('x', 666, "a string"));
    }

    public void test_mismatches_on_first_field_inequality() {
        assertMismatchDescription(
                "'s' was \"different\"",
                WITH_PUBLIC_FIELDS_MATCHER, new WithPublicFields('x', 666, "different"));
    }

    public void test_mismatches_on_inherited_field() {
        assertMismatchDescription(
                "'c' was \"y\"",
                WITH_PUBLIC_FIELDS_MATCHER, new WithPublicFields('y', 666, "a string"));
    }

    public static class WithPublicFields extends Parent {
        public final int i;
        public final String s;

        public WithPublicFields(char c, int i, String s) {
            super(c);
            this.i = i;
            this.s = s;
        }
    }

    public static class Parent {
        public final char c;

        public Parent(char c) {
            this.c = c;
        }
    }
}
