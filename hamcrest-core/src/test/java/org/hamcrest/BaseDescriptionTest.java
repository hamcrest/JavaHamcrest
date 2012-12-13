package org.hamcrest;

import junit.framework.TestCase;

public class BaseDescriptionTest extends TestCase {

    private final StringBuilder result = new StringBuilder();

    private final BaseDescription baseDescription = new BaseDescription() {
        @Override protected void append(char c) {
            result.append(c);
        }
    };

    public void testDescribesAppendedNullValue() {
        baseDescription.appendValue(null);
        assertEquals("null", result.toString());
    }

    public void testQuotesAppendedStringValue() {
        baseDescription.appendValue("foo");
        assertEquals("\"foo\"", result.toString());
    }

    public void testQuotesAppendedCharacterValue() {
        baseDescription.appendValue('f');
        assertEquals("\"f\"", result.toString());
    }

    public void testBracketsAppendedShortValue() {
        baseDescription.appendValue(Short.valueOf("2"));
        assertEquals("<2s>", result.toString());
    }

    public void testBracketsAppendedLongValue() {
        baseDescription.appendValue(Long.valueOf("2"));
        assertEquals("<2L>", result.toString());
    }

    public void testBracketsAppendedFloatValue() {
        baseDescription.appendValue(Float.valueOf("1.2"));
        assertEquals("<1.2F>", result.toString());
    }

    public void testDescribesAppendedArrayValue() {
        baseDescription.appendValue(new String[] {"2", "3"});
        assertEquals("[\"2\", \"3\"]", result.toString());
    }

    public void testBracketsAppendedObjectValue() {
        final Object value = new Object();
        baseDescription.appendValue(value);
        assertEquals("<" + value.toString() + ">", result.toString());
    }
    
    public void testSafelyDescribesAppendedValueOfObjectWhoseToStringThrowsAnException() {
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
