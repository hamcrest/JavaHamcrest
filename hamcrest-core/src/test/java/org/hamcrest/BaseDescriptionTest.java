package org.hamcrest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

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
    describesAppendedIterableValue() {
        baseDescription.appendValue(new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return Arrays.asList("2", "3").iterator();
            }
        });
        assertEquals("<[\"2\", \"3\"]>", result.toString());
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
