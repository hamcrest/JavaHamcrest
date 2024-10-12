package org.hamcrest.object;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import java.util.EventObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.test.MatcherAssertions.assertMismatchDescription;
import static org.hamcrest.object.IsEventFrom.eventFrom;

public class IsEventFromTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return eventFrom(null);
    }

    @Test
    public void testEvaluatesToTrueIfArgumentIsAnEventObjectFiredByASpecifiedSource() {
        Object o = "Source";
        EventObject ev = new EventObject(o);
        EventObject ev2 = new EventObject("source 2");

        Matcher<EventObject> isEventMatcher = eventFrom(o);

        assertThat(ev, isEventMatcher);
        assertMismatchDescription("source was \"source 2\"", isEventMatcher, ev2);
    }

    private static class DerivedEvent extends EventObject {
        private static final long serialVersionUID = 1L;

        public DerivedEvent(Object source) {
            super(source);
        }
    }

    @Test
    public void testCanTestForSpecificEventClasses() {
        Object o = new Object();
        DerivedEvent goodEv = new DerivedEvent(o);
        DerivedEvent wrongSource = new DerivedEvent("wrong source");
        EventObject wrongType = new EventObject(o);
        EventObject wrongSourceAndType = new EventObject(new Object());

        Matcher<EventObject> isEventMatcher = IsEventFrom.eventFrom(DerivedEvent.class, o);

        assertThat(goodEv, isEventMatcher);
        assertMismatchDescription("source was \"wrong source\"", isEventMatcher, wrongSource);
        assertMismatchDescription("item type was java.util.EventObject", isEventMatcher, wrongType);
        assertMismatchDescription("item type was java.util.EventObject", isEventMatcher, wrongSourceAndType);
    }

}
