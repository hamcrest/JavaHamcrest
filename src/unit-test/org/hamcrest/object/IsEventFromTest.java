/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.object;

import org.hamcrest.AbstractMatcherTest;

import java.util.EventObject;


public class IsEventFromTest extends AbstractMatcherTest {
    public void testEvaluatesToTrueIfArgumentIsAnEventObjectFiredByASpecifiedSource() {
        Object o = new Object();
        EventObject ev = new EventObject(o);
        EventObject ev2 = new EventObject(new Object());

        IsEventFrom p = new IsEventFrom(o);

        assertTrue(p.match(ev));
        assertTrue("p should match to false for an event not from o",
                !p.match(ev2));
        assertTrue("p should match to false for objects that are not events",
                !p.match(o));
    }

    private static class DerivedEvent extends EventObject {
        private static final long serialVersionUID = 1L;

        public DerivedEvent(Object source) {
            super(source);
        }
    }

    public void testCanTestForSpecificEventClasses() {
        Object o = new Object();
        DerivedEvent good_ev = new DerivedEvent(o);
        DerivedEvent wrong_source = new DerivedEvent(new Object());
        EventObject wrong_type = new EventObject(o);
        EventObject wrong_source_and_type = new EventObject(new Object());

        IsEventFrom p = new IsEventFrom(DerivedEvent.class, o);

        assertTrue(p.match(good_ev));
        assertTrue("p should match to false for an event not from o",
                !p.match(wrong_source));
        assertTrue("p should match to false for an event of the wrong type",
                !p.match(wrong_type));
        assertTrue("p should match to false for an event of the wrong type " +
                "and from the wrong source",
                !p.match(wrong_source_and_type));
    }
}
