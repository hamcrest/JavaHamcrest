/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;

import org.hamcrest.internal.StringDescription;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * @since 1.0
 */
public class Formatting {
    public static String toReadableString(Object element) {
        if (element == null) {
            return "null";
        } else if (element instanceof String) {
            return toJavaSyntax((String) element);
        } else if (element instanceof Character) {
            return "'" + toJavaSyntax(((Character) element).charValue()) + "'";
        } else if (element instanceof Short) {
            return "<" + element.toString() + "s>";
        } else if (element instanceof Long) {
            return "<" + element.toString() + "L>";
        } else if (element instanceof Float) {
            return "<" + element.toString() + "F>";
        } else if (element.getClass().isArray()) {
            Description description = new StringDescription();
            join(element, description);
            return description.toString();
        } else {
            return "<" + element.toString() + ">";
        }
    }

    private static String toJavaSyntax(String unformatted) {
        StringBuffer formatted = new StringBuffer();
        formatted.append('"');
        for (int i = 0; i < unformatted.length(); i++) {
            formatted.append(toJavaSyntax(unformatted.charAt(i)));
        }
        formatted.append('"');

        return formatted.toString();
    }

    private static String toJavaSyntax(char ch) {
        switch (ch) {
            case '"':
                return "\\\"";

            case '\n':
                return "\\n";

            case '\r':
                return "\\r";

            case '\t':
                return "\\t";

            default:
                return new String(new char[]{ch});
        }
    }

    public static void join(Object array, Description buf) {
        join(array, buf, "[", "]");
    }

    public static void join(Collection collection, Description buf, String prefix, String postfix) {
        join(collection.toArray(), buf, prefix, postfix);
    }

    public static void join(Collection collection, Description buf,
                                    String prefix, String separator, String postfix) {
        join(collection.toArray(), buf, prefix, separator, postfix);
    }

    private static void join(Object array, Description buf, String prefix, String postfix) {
        join(array, buf, prefix, ", ", postfix);
    }

    public static void join(Object array, Description buf,
                                    String prefix, String separator, String postfix) {
        buf.append(prefix);

        for (int i = 0; i < Array.getLength(array); i++) {
            if (i > 0) buf.append(separator);
            buf.append(toReadableString(Array.get(array, i)));
        }

        buf.append(postfix);
    }

    public static String classShortName(Class c) {
        String fullTypeName = c.getName();
        return fullTypeName.substring(Math.max(fullTypeName.lastIndexOf('.'), fullTypeName.lastIndexOf('$')) + 1);
    }
}