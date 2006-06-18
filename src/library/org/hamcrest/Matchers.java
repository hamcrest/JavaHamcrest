/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest;

import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsCompatibleType;
import org.hamcrest.core.IsIn;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;

import java.util.Collection;

public class Matchers {

    public static final Matcher ANYTHING = new IsAnything();
    public static final Matcher NULL = new IsNull();
    public static final Matcher NOT_NULL = new IsNot(NULL);

    public static IsInstanceOf isA(Class operandClass) {
        return new IsInstanceOf(operandClass);
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
