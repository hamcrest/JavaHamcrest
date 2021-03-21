/**
 * BSD License
 *
 * Copyright (c) 2000-2021 www.hamcrest.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * Neither the name of Hamcrest nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hamcrest.object;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.util.EventObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.object.IsEventFrom.eventFrom;


public class IsEventFromTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return eventFrom(null);
    }

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
