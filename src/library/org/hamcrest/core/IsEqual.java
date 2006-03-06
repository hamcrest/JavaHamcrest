/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Formatting;
import org.hamcrest.Matcher;

import java.lang.reflect.Array;


/**
 * Is the value equal to another value, as tested by the
 * {@link java.lang.Object#equals} invokedMethod?
 */
public class IsEqual implements Matcher {
    private final Object object;

    public IsEqual(Object equalArg) {
        object = equalArg;
    }

    public boolean match(Object arg) {
        return areEqual(object, arg);
    }

    public void describeTo(StringBuffer buffer) {
        buffer.append("eq(")
                .append(Formatting.toReadableString(object))
                .append(")");
    }

    private static boolean areEqual(Object o1, Object o2) {
        if (o1 == null || o2 == null) {
            return o1 == null && o2 == null;
        } else if (isArray(o1)) {
            return isArray(o2) && areArraysEqual(o1, o2);
        } else {
            return o1.equals(o2);
        }
    }

    private static boolean areArraysEqual(Object o1, Object o2) {
        return areArrayLengthsEqual(o1, o2)
                && areArrayElementsEqual(o1, o2);
    }

    private static boolean areArrayLengthsEqual(Object o1, Object o2) {
        return Array.getLength(o1) == Array.getLength(o2);
    }

    private static boolean areArrayElementsEqual(Object o1, Object o2) {
        for (int i = 0; i < Array.getLength(o1); i++) {
            if (!areEqual(Array.get(o1, i), Array.get(o2, i))) return false;
        }
        return true;
    }

    private static boolean isArray(Object o) {
        return o.getClass().isArray();
    }
}
