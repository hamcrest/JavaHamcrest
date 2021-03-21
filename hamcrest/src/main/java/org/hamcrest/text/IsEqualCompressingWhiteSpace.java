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
package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Tests if a string is equal to another string, compressing any changes in whitespace.
 */
public class IsEqualCompressingWhiteSpace extends TypeSafeMatcher<String> {

    // TODO: Replace String with CharSequence to allow for easy interoperability between
    //       String, StringBuffer, StringBuilder, CharBuffer, etc (joe).

    private final String string;

    public IsEqualCompressingWhiteSpace(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Non-null value required");
        }
        this.string = string;
    }

    @Override
    public boolean matchesSafely(String item) {
        return stripSpaces(string).equals(stripSpaces(item));
    }
    
    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
      mismatchDescription.appendText("was ").appendValue(item);
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("a string equal to ")
                .appendValue(string)
                .appendText(" compressing white space");
    }

    public String stripSpaces(String toBeStripped) {
        return toBeStripped.replaceAll("[\\p{Z}\\p{C}]+", " ").trim();
    }

    /**
     * @deprecated {@link #equalToCompressingWhiteSpace(String)}
     * @param expectedString
     *     the expected value of matched strings
     */
    public static Matcher<String> equalToIgnoringWhiteSpace(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is equal to
     * the specified expectedString, when whitespace differences are (mostly) ignored.  To be
     * exact, the following whitespace rules are applied:
     * <ul>
     *   <li>all leading and trailing whitespace of both the expectedString and the examined string are ignored</li>
     *   <li>any remaining whitespace, appearing within either string, is collapsed to a single space before comparison</li>
     * </ul>
     * For example:
     * <pre>assertThat("   my\tfoo  bar ", equalToCompressingWhiteSpace(" my  foo bar"))</pre>
     *
     * @param expectedString
     *     the expected value of matched strings
     */
    public static Matcher<String> equalToCompressingWhiteSpace(String expectedString) {
        return new IsEqualCompressingWhiteSpace(expectedString);
    }

}
