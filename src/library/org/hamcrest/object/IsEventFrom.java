/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.EventObject;


/**
 * Tests if the value is an event announced by a specific object.
 */
public class IsEventFrom implements Matcher {
    private final Class eventClass;
    private final Object source;

    /**
     * Constructs an IsEventFrom predicate that returns true for any object
     * derived from {@link java.util.EventObject}announced by <var>source
     * </var>.
     */
    public IsEventFrom(Object source) {
        this(EventObject.class, source);
    }

    /**
     * Constructs an IsEventFrom predicate that returns true for any object
     * derived from <var>event_class</var> announced by <var>source</var>.
     */
    public IsEventFrom(Class eventClass, Object source) {
        this.eventClass = eventClass;
        this.source = source;
    }

    public boolean match(Object o) {
        return (o instanceof EventObject)
                && eventClass.isInstance(o)
                && eventHasSameSource((EventObject) o);
    }

    private boolean eventHasSameSource(EventObject ev) {
        return ev.getSource() == source;
    }

    public void describeTo(Description description) {
        description.appendText("an event of type ")
                .appendText(eventClass.getName())
                .appendText(" from ")
                .appendValue(source);
    }
}
