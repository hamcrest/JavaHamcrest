/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.hamcrest.core.IsSame.theInstance;

import org.hamcrest.Matcher;
import org.junit.Test;


public final class IsSameTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = sameInstance("irrelevant");
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
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
