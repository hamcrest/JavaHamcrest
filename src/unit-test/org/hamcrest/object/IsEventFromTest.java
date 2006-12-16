/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.object;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.object.IsEventFrom.eventFrom;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.util.EventObject;


public class IsEventFromTest extends AbstractMatcherTest {

    protected Matcher<?> createMatcher() {
        return eventFrom(null);
    }

    public void testEvaluatesToTrueIfArgumentIsAnEventObjectFiredByASpecifiedSource() {
        Object o = new Object();
        EventObject ev = new EventObject(o);
        EventObject ev2 = new EventObject(new Object());

        Matcher<EventObject> isEventMatcher = eventFrom(o);

        assertThat(ev, isEventMatcher);
        assertThat(ev2, not(isEventMatcher));
    }

    private static class DerivedEvent extends EventObject {
        private static final long serialVersionUID = 1L;

        public DerivedEvent(Object source) {
            super(source);
        }
    }

    public void testCanTestForSpecificEventClasses() {
        Object o = new Object();
        DerivedEvent goodEv = new DerivedEvent(o);
        DerivedEvent wrongSource = new DerivedEvent(new Object());
        EventObject wrongType = new EventObject(o);
        EventObject wrongSourceAndType = new EventObject(new Object());

        Matcher<EventObject> isEventMatcher = IsEventFrom.eventFrom(DerivedEvent.class, o);

        assertThat(goodEv, isEventMatcher);
        assertThat(wrongSource, not(isEventMatcher));
        assertThat(wrongType, not(isEventMatcher));
        assertThat(wrongSourceAndType, not(isEventMatcher));
    }
}
