/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Matcher;


/**
 * Tests whether the value is an instance of a class.
 */
public class IsInstanceOf implements Matcher {
    private final Class theClass;

    /**
     * Creates a new instance of IsInstanceOf
     *
     * @param theClass The predicate evaluates to true for instances of this class
     *                 or one of its subclasses.
     */
    public IsInstanceOf(Class theClass) {
        this.theClass = theClass;
    }

    public boolean match(Object arg) {
        return theClass.isInstance(arg);
    }

    public void describeTo(StringBuffer buffer) {
        buffer.append("an instance of ")
                .append(theClass.getName());
    }
}
