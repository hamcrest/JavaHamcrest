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
package org.hamcrest;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public final class MatcherAssertTest {

    @Test public void
    includesDescriptionOfTestedValueInErrorMessage() {
        String expected = "expected";
        String actual = "actual";
        String endLine = System.lineSeparator();

        String expectedMessage = "identifier" + endLine + "Expected: \"expected\"" + endLine + "     but: was \"actual\"";

        try {
            assertThat("identifier", actual, equalTo(expected));
        }
        catch (AssertionError e) {
            assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
        }

        fail("should have failed");
    }

    @Test public void
    descriptionCanBeElided() {
        String expected = "expected";
        String actual = "actual";
        String endLine = System.lineSeparator();

        String expectedMessage = endLine + "Expected: \"expected\"" + endLine + "     but: was \"actual\"";

        try {
            assertThat(actual, equalTo(expected));
        }
        catch (AssertionError e) {
            assertTrue(e.getMessage().startsWith(expectedMessage));
            return;
        }

        fail("should have failed");
    }

    @Test public void
    canTestBooleanDirectly() {
        assertThat("success reason message", true);

        try {
            assertThat("failing reason message", false);
        }
        catch (AssertionError e) {
            assertEquals("failing reason message", e.getMessage());
            return;
        }

        fail("should have failed");
    }

    @Test public void
    includesMismatchDescription() {
        Matcher<String> matcherWithCustomMismatchDescription = new BaseMatcher<String>() {
            @Override
            public boolean matches(Object item) {
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Something cool");
            }

            @Override
            public void describeMismatch(Object item, Description mismatchDescription) {
                mismatchDescription.appendText("Not cool");
            }
        };

        String endLine = System.lineSeparator();
        String expectedMessage = endLine + "Expected: Something cool" + endLine + "     but: Not cool";

        try {
            assertThat("Value", matcherWithCustomMismatchDescription);
            fail("should have failed");
        }
        catch (AssertionError e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test public void
    canAssertSubtypes() {
        assertThat(1, equalTo((Number) 1));
    }
}
