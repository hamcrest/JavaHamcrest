/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.object;

import java.util.EventObject;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;


/**
 * Tests if the value is an event announced by a specific object.
 */
public class IsEventFrom extends TypeSafeDiagnosingMatcher<EventObject> {
    private final Class<?> eventClass;
    private final Object source;

    public IsEventFrom(Class<?> eventClass, Object source) {
        this.eventClass = eventClass;
        this.source = source;
    }

    @Override
    public boolean matchesSafely(EventObject item, Description mismatchDescription) {
        if (!eventClass.isInstance(item)) {
          mismatchDescription.appendText("item type was " + item.getClass().getName());
          return false;
        }
        
        if (!eventHasSameSource(item)) {
          mismatchDescription.appendText("source was ").appendValue(item.getSource());
          return false;
        }
        return true;
    }

    
    private boolean eventHasSameSource(EventObject ev) {
        return ev.getSource() == source;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an event of type ")
                .appendText(eventClass.getName())
                .appendText(" from ")
                .appendValue(source);
    }

    /**
     * Creates a matcher of {@link java.util.EventObject} that matches any object
     * derived from <var>eventClass</var> announced by <var>source</var>.
     * </p>
     * For example:
     * <pre>assertThat(myEvent, is(eventFrom(PropertyChangeEvent.class, myBean)))</pre>
     * 
     * @param eventClass
     *     the class of the event to match on
     * @param source
     *     the source of the event
     */
    @Factory
    public static Matcher<EventObject> eventFrom(Class<? extends EventObject> eventClass, Object source) {
        return new IsEventFrom(eventClass, source);
    }

    /**
     * Creates a matcher of {@link java.util.EventObject} that matches any EventObject
     * announced by <var>source</var>.
     * </p>
     * For example:
     * <pre>assertThat(myEvent, is(eventFrom(myBean)))</pre>
     * 
     * @param source
     *     the source of the event
     */
    @Factory
    public static Matcher<EventObject> eventFrom(Object source) {
        return eventFrom(EventObject.class, source);
    }
}
