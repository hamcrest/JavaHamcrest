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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.EventObject;


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
     * For example:
     * <pre>assertThat(myEvent, is(eventFrom(PropertyChangeEvent.class, myBean)))</pre>
     * 
     * @param eventClass
     *     the class of the event to match on
     * @param source
     *     the source of the event
     */
    public static Matcher<EventObject> eventFrom(Class<? extends EventObject> eventClass, Object source) {
        return new IsEventFrom(eventClass, source);
    }

    /**
     * Creates a matcher of {@link java.util.EventObject} that matches any EventObject
     * announced by <var>source</var>.
     * For example:
     * <pre>assertThat(myEvent, is(eventFrom(myBean)))</pre>
     * 
     * @param source
     *     the source of the event
     */
    public static Matcher<EventObject> eventFrom(Object source) {
        return eventFrom(EventObject.class, source);
    }
}
