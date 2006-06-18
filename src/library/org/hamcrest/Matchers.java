/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;

import org.hamcrest.core.And;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsCompatibleType;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsIn;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.Or;
import org.hamcrest.internal.StringDescription;

import java.util.Collection;

public class Matchers {

    public static final Matcher ANYTHING = new IsAnything();
    public static final Matcher NULL = new IsNull();
    public static final Matcher NOT_NULL = new IsNot(NULL);
    public static final Matcher IS_TRUE = new IsEqual(Boolean.TRUE);
    public static final Matcher IS_FALSE = new IsEqual(Boolean.FALSE);

    public static <T> void assertThat(T value, Matcher<T> isMatchedBy) {
        if (!isMatchedBy.match(value)) {
            Description description = new StringDescription();
            description.appendText("\nExpected: ");
            isMatchedBy.describeTo(description);
            description.appendText("\n    got : ").appendValue(value).appendText("\n");
            throw new AssertionError(description.toString());
        }
    }

    public static IsEqual eq(Object operand) {
        return new IsEqual(operand);
    }

    public static IsInstanceOf isA(Class operandClass) {
        return new IsInstanceOf(operandClass);
    }

    public static <T> Matcher<T> not(Matcher<T> c) {
        return new IsNot<T>(c);
    }

    public static <T> Matcher<T> and(Matcher<T> left, Matcher<T> right) {
        return new And<T>(left, right);
    }

    public static <T> Matcher<T> or(Matcher<T> left, Matcher<T> right) {
        return new Or<T>(left, right);
    }

    public static IsCompatibleType compatibleType(Class baseType) {
        return new IsCompatibleType(baseType);
    }

    public static IsIn isIn(Collection collection) {
        return new IsIn(collection);
    }

    public static IsIn isIn(Object[] array) {
        return new IsIn(array);
    }

    /**
     * Temporary Matcher... don't use this.. it will go away!
     */
    public static Matcher<String> isTwoXs() {
        return new Matcher<String>() {
            public boolean match(String string) {
                return string.equals("xx");
            }

            public void describeTo(Description description) {
                description.appendText("Two x's");
            }
        };
    }
}
