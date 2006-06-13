package org.hamcrest.internal;

import org.hamcrest.Description;

import java.lang.reflect.Array;

public class StringDescription implements Description {

    private final StringBuffer buffer;

    public StringDescription() {
        this(new StringBuffer());
    }

    public StringDescription(StringBuffer buffer) {
        this.buffer = buffer;
    }

    public Description appendText(String text) {
        buffer.append(text);
        return this;
    }

    public Description appendValue(Object value) {
        if (value == null) {
            buffer.append("null");
        } else if (value instanceof String) {
            toJavaSyntax(buffer, (String) value);
        } else if (value instanceof Character) {
            buffer.append('"');
            toJavaSyntax(buffer, (Character) value);
            buffer.append('"');
        } else if (value instanceof Short) {
            buffer.append('<').append(value).append("s>");
        } else if (value instanceof Long) {
            buffer.append('<').append(value).append("L>");
        } else if (value instanceof Float) {
            buffer.append('<').append(value).append("F>");
        } else if (value.getClass().isArray()) {
            buffer.append('[');
            for (int i = 0; i < Array.getLength(value); i++) {
                if (i > 0) {
                    buffer.append(", ");
                }
                appendValue(Array.get(value, i));
            }
            buffer.append(']');
        } else {
            buffer.append('<').append(value).append('>');
        }
        return this;
    }

    private void toJavaSyntax(StringBuffer buffer, String unformatted) {
        buffer.append('"');
        for (int i = 0; i < unformatted.length(); i++) {
            toJavaSyntax(buffer, unformatted.charAt(i));
        }
        buffer.append('"');
    }

    private void toJavaSyntax(StringBuffer buffer, char ch) {
        switch (ch) {
            case '"':
                buffer.append("\\\"");
                break;
            case '\n':
                buffer.append("\\n");
                break;
            case '\r':
                buffer.append("\\r");
                break;
            case '\t':
                buffer.append("\\t");
                break;
            default:
                buffer.append(ch);
        }
    }

    public String toString() {
        return buffer.toString();
    }
}
