/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;


/**
 * Tests whether the value is an instance of a class.
 */
public class IsInstanceOf implements Matcher<Class> {
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

    public boolean match(Class cls) {
        return theClass.isInstance(cls);
    }

    public void describeTo(Description description) {
        description.appendText("an instance of ")
                .appendText(theClass.getName());
    }
}
