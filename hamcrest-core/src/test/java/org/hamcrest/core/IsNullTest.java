/*  Copyright (c) 2000-20010 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

import org.hamcrest.Matcher;
import org.junit.Test;


public final class IsNullTest {

    private final Matcher<Object> nullMatcher = nullValue();
    private final Matcher<Object> notNullMatcher = notNullValue();

    @Test public void
    copesWithNullsAndUnknownTypes() {
        assertNullSafe(nullMatcher);
        assertUnknownTypeSafe(nullMatcher);
        
        assertNullSafe(notNullMatcher);
        assertUnknownTypeSafe(notNullMatcher);
    }

    @Test public void
    evaluatesToTrueIfArgumentIsNull() {
        assertMatches(nullMatcher, null);
        assertDoesNotMatch(nullMatcher, new Object());
        
        assertMatches(notNullMatcher, new Object());
        assertDoesNotMatch(notNullMatcher, null);
    }
    
    @Test public void
    supportsStaticTyping() {
        requiresStringMatcher(nullValue(String.class));
        requiresStringMatcher(notNullValue(String.class));
    }

    private void requiresStringMatcher(@SuppressWarnings("unused") Matcher<String> arg) {
        // no-op
    }
}
