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

import static org.junit.Assert.assertEquals;

public final class BaseDescriptionTest {

    private final StringBuilder result = new StringBuilder();

    private final BaseDescription baseDescription = new BaseDescription() {
        @Override protected void append(char c) {
            result.append(c);
        }
    };

    @Test public void 
    describesAppendedNullValue() {
        baseDescription.appendValue(null);
        assertEquals("null", result.toString());
    }

    @Test public void 
    quotesAppendedStringValue() {
        baseDescription.appendValue("foo");
        assertEquals("\"foo\"", result.toString());
    }

    @Test public void 
    quotesAppendedCharacterValue() {
        baseDescription.appendValue('f');
        assertEquals("\"f\"", result.toString());
    }

    @Test public void
    quotesAppendedTab() {
        baseDescription.appendValue('\t');
        assertEquals("\"\\t\"", result.toString());
    }

    @Test public void
    quotesAppendedNewLine() {
        baseDescription.appendValue('\n');
        assertEquals("\"\\n\"", result.toString());
    }

    @Test public void
    quotesAppendedLineReturn() {
        baseDescription.appendValue('\r');
        assertEquals("\"\\r\"", result.toString());
    }

    @Test public void
    quotesAppendedBackslash() {
        baseDescription.appendValue('\\');
        assertEquals("\"\\\\\"", result.toString());
    }

    @Test public void
    quotesAppendedDoubleQuotes() {
        baseDescription.appendValue('"');
        assertEquals("\"\\\"\"", result.toString());
    }

    @Test public void
    bracketsAppendedByteValue() {
        baseDescription.appendValue(Byte.valueOf("2"));
        assertEquals("<2b>", result.toString());
    }

    @Test public void
    bracketsAppendedShortValue() {
        baseDescription.appendValue(Short.valueOf("2"));
        assertEquals("<2s>", result.toString());
    }

    @Test public void 
    bracketsAppendedLongValue() {
        baseDescription.appendValue(Long.valueOf("2"));
        assertEquals("<2L>", result.toString());
    }

    @Test public void 
    bracketsAppendedFloatValue() {
        baseDescription.appendValue(Float.valueOf("1.2"));
        assertEquals("<1.2F>", result.toString());
    }

    @Test public void 
    describesAppendedArrayValue() {
        baseDescription.appendValue(new String[] {"2", "3"});
        assertEquals("[\"2\", \"3\"]", result.toString());
    }

    @Test public void 
    bracketsAppendedObjectValue() {
        final Object value = new Object();
        baseDescription.appendValue(value);
        assertEquals("<" + value.toString() + ">", result.toString());
    }
    
    @Test public void 
    safelyDescribesAppendedValueOfObjectWhoseToStringThrowsAnException() {
        final Object value = new Object() {
            @Override public String toString() {
                throw new UnsupportedOperationException();
            }
        };
        
        final String expected = value.getClass().getName() + "@" + Integer.toHexString(value.hashCode());
        baseDescription.appendValue(value);
        assertEquals("<" + expected + ">", result.toString());
    }
}
