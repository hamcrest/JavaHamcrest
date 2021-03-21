package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;


public final class IsNullTest {

    private final Matcher<Object> nullMatcher = nullValue();
    private final Matcher<Object> notNullMatcher = notNullValue();

    @Test public void
    copesWithNullsAndUnknownTypes() {
        assertNullSafe(nullMatcher);
        
        assertNullSafe(notNullMatcher);
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
