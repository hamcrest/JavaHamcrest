package org.hamcrest;

import static java.lang.String.valueOf;
import java.util.Arrays;
import java.util.Iterator;
import java.io.IOException;

import org.hamcrest.internal.ArrayIterator;
import org.hamcrest.internal.SelfDescribingValueIterator;

/**
 * A {@link Description} that is stored as a string.
 */
public class StringDescription implements Description {

    private final Appendable out;

    public StringDescription() {
        this(new StringBuilder());
    }

    public StringDescription(Appendable out) {
        this.out = out;
    }
    
    /**
     * Return the description of a {@link SelfDescribing} object as a String.
     * 
     * @param selfDescribing
     *   The object to be described.
     * @return
     *   The description of the object.
     */
    public static String toString(SelfDescribing selfDescribing) {
        StringDescription description = new StringDescription();
        selfDescribing.describeTo(description);
        return description.toString();
    }

    public Description appendText(String text) {
        append(text);
        return this;
    }

    public Description appendValue(Object value) {
        if (value == null) {
            append("null");
        } else if (value instanceof String) {
            toJavaSyntax((String) value);
        } else if (value instanceof Character) {
            append('"');
            toJavaSyntax((Character) value);
            append('"');
        } else if (value instanceof Short) {
            append('<');
            append(valueOf(value));
            append("s>");
        } else if (value instanceof Long) {
            append('<');
            append(valueOf(value));
            append("L>");
        } else if (value instanceof Float) {
            append('<');
            append(valueOf(value));
            append("F>");
        } else if (value.getClass().isArray()) {
        	appendValueList("[",", ","]", new ArrayIterator(value));
        } else {
            append('<');
            append(valueOf(value));
            append('>');
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
        append(start);
        while (i.hasNext()) {
            if (separate) append(separator);
            i.next().describeTo(this);
            separate = true;
        }
        append(end);

        return this;
    }

    private void append(String str) {
        try {
            out.append(str);
        } catch (IOException e) {
            throw new RuntimeException("Could not write description", e);
        }
    }

    private void append(char c) {
        try {
            out.append(c);
        } catch (IOException e) {
            throw new RuntimeException("Could not write description", e);
        }
    }

    private void toJavaSyntax(String unformatted) {
        append('"');
        for (int i = 0; i < unformatted.length(); i++) {
            toJavaSyntax(unformatted.charAt(i));
        }
        append('"');
    }

    private void toJavaSyntax(char ch) {
        switch (ch) {
            case '"':
                append("\\\"");
                break;
            case '\n':
                append("\\n");
                break;
            case '\r':
                append("\\r");
                break;
            case '\t':
                append("\\t");
                break;
            default:
                append(ch);
        }
    }

    /**
     * Returns the description as a string.
     */
    public String toString() {
        return out.toString();
    }
}
