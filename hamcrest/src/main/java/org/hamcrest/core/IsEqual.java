package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.lang.reflect.Array;


/**
 * Is the value equal to another value, as tested by the
 * {@link java.lang.Object#equals} invokedMethod?
 */
public class IsEqual<T> extends BaseMatcher<T> {
    private final Object expectedValue;

    public IsEqual(T equalArg) {
        expectedValue = equalArg;
    }

    @Override
    public boolean matches(Object actualValue) {
        return areEqual(actualValue, expectedValue);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expectedValue);
    }

    private static boolean areEqual(Object actual, Object expected) {
        if (actual == null) {
            return expected == null;
        }
        
        if (expected != null && isArray(actual)) {
            return isArray(expected) && areArraysEqual(actual, expected);
        }
        
        return actual.equals(expected);
    }

    private static boolean areArraysEqual(Object actualArray, Object expectedArray) {
        return areArrayLengthsEqual(actualArray, expectedArray) && areArrayElementsEqual(actualArray, expectedArray);
    }

    private static boolean areArrayLengthsEqual(Object actualArray, Object expectedArray) {
        return Array.getLength(actualArray) == Array.getLength(expectedArray);
    }

    private static boolean areArrayElementsEqual(Object actualArray, Object expectedArray) {
        for (int i = 0; i < Array.getLength(actualArray); i++) {
            if (!areEqual(Array.get(actualArray, i), Array.get(expectedArray, i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isArray(Object o) {
        return o.getClass().isArray();
    }

    /**
     * Creates a matcher that matches when the examined object is logically equal to the specified
     * <code>operand</code>, as determined by calling the {@link java.lang.Object#equals} method on
     * the <b>examined</b> object.
     * 
     * <p>If the specified operand is <code>null</code> then the created matcher will only match if
     * the examined object's <code>equals</code> method returns <code>true</code> when passed a
     * <code>null</code> (which would be a violation of the <code>equals</code> contract), unless the
     * examined object itself is <code>null</code>, in which case the matcher will return a positive
     * match.</p>
     * 
     * <p>The created matcher provides a special behaviour when examining <code>Array</code>s, whereby
     * it will match if both the operand and the examined object are arrays of the same length and
     * contain items that are equal to each other (according to the above rules) <b>in the same
     * indexes</b>.</p> 
     * For example:
     * <pre>
     * assertThat("foo", equalTo("foo"));
     * assertThat(new String[] {"foo", "bar"}, equalTo(new String[] {"foo", "bar"}));
     * </pre>
     * 
     */
    public static <T> Matcher<T> equalTo(T operand) {
        return new IsEqual<>(operand);
    }

    /**
     * Creates an {@link org.hamcrest.core.IsEqual} matcher that does not enforce the values being
     * compared to be of the same static type.
     */
    public static <T> Matcher<T> equalToObject(T operand) {
        return new IsEqual<>(operand);
    }
}
