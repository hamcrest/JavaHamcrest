package org.hamcrest;

import java.util.Arrays;
import java.util.Iterator;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.internal.ArrayIterator;
import org.hamcrest.internal.SelfDescribingValueIterator;

public class StringDescription implements Description {
    private final StringBuffer buffer;

    public StringDescription() {
        this(new StringBuffer());
    }

    public StringDescription(StringBuffer buffer) {
        this.buffer = buffer;
    }
    
    public static String toString(SelfDescribing selfDescribing) {
        StringDescription description = new StringDescription();
        selfDescribing.describeTo(description);
        return description.toString();
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
        	appendValueList("[",", ","]", new ArrayIterator(value));
        } else {
            buffer.append('<').append(value).append('>');
        }
        return this;
    }
    
    public <T> Description appendValueList(String start, String separator, String end, T... values) {
        return appendValueList(start, separator, end, Arrays.asList(values));
	}
    
	public <T> Description appendValueList(String start, String separator, String end, Iterable<T> values) {
		return appendValueList(start, separator, end, values.iterator());
	}
	
	private <T> Description appendValueList(String start, String separator, String end, Iterator<T> values) {
		return appendList(start, separator, end, new SelfDescribingValueIterator<T>(values));
	}
	
    public Description appendList(String start, String separator, String end, Iterable<? extends SelfDescribing> values) {
        return appendList(start, separator, end, values.iterator());
    }

    private Description appendList(String start, String separator, String end, Iterator<? extends SelfDescribing> i) {
        boolean separate = false;
        buffer.append(start);
        while (i.hasNext()) {
            if (separate) buffer.append(separator);
            i.next().describeTo(this);
            separate = true;
        }
        buffer.append(end);
        
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
