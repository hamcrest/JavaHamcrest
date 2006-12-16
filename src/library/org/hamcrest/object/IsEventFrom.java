/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import java.util.EventObject;


/**
 * Tests if the value is an event announced by a specific object.
 */
public class IsEventFrom extends TypeSafeMatcher<EventObject> {
    private final Class eventClass;
    private final Object source;

    public IsEventFrom(Class eventClass, Object source) {
        this.eventClass = eventClass;
        this.source = source;
    }

    public boolean matchesSafely(EventObject item) {
        return eventClass.isInstance(item)
                && eventHasSameSource(item);
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

    /**
     * Constructs an IsEventFrom Matcher that returns true for any object
     * derived from <var>eventClass</var> announced by <var>source</var>.
     */
    @Factory
    public static Matcher<EventObject> eventFrom(Class<? extends EventObject> eventClass, Object source) {
        return new IsEventFrom(eventClass, source);
    }

    /**
     * Constructs an IsEventFrom Matcher that returns true for any object
     * derived from {@link java.util.EventObject} announced by <var>source
     * </var>.
     */
    @Factory
    public static Matcher<EventObject> eventFrom(Object source) {
        return eventFrom(EventObject.class, source);
    }
}
