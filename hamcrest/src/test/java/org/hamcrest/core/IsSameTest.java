package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.hamcrest.core.IsSame.theInstance;


public final class IsSameTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = sameInstance("irrelevant");
        
        assertNullSafe(matcher);
    }

    @Test public void
    evaluatesToTrueIfArgumentIsReferenceToASpecifiedObject() {
        Object o1 = new Object();
        Matcher<Object> matcher = sameInstance(o1);

        assertMatches(matcher, o1);
        assertDoesNotMatch(matcher, new Object());
    }

    @Test public void
    alternativeFactoryMethodAlsoMatchesOnlyIfArgumentIsReferenceToASpecifiedObject() {
        Object o1 = new Object();
        Matcher<Object> matcher = theInstance(o1);

        assertMatches(matcher, o1);
        assertDoesNotMatch(matcher, new Object());
    }

    @Test public void
    returnsReadableDescriptionFromToString() {
        assertDescription("sameInstance(\"ARG\")", sameInstance("ARG"));
    }

    @Test public void
    returnsReadableDescriptionFromToStringWhenInitialisedWithNull() {
        assertDescription("sameInstance(null)", sameInstance(null));
    }
}
