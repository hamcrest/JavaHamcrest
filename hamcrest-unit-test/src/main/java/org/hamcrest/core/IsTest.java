package org.hamcrest.core;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return is("something");
    }

    public void testJustMatchesTheSameWayTheUnderylingMatcherDoes() {
        assertMatches("should match", is(equalTo(true)), true);
        assertMatches("should match", is(equalTo(false)), false);
        assertDoesNotMatch("should not match", is(equalTo(true)), false);
        assertDoesNotMatch("should not match", is(equalTo(false)), true);
    }

    public void testGeneratesIsPrefixInDescription() {
        assertDescription("is <true>", is(equalTo(true)));
    }

    public void testProvidesConvenientShortcutForIsEqualTo() {
        assertMatches("should match", is("A"), "A");
        assertMatches("should match", is("B"), "B");
        assertDoesNotMatch("should not match", is("A"), "B");
        assertDoesNotMatch("should not match", is("B"), "A");
        assertDescription("is \"A\"", is("A"));
    }

    public void testProvidesConvenientShortcutForIsInstanceOf() {
        assertTrue("should match", is(String.class).matches("A"));
        assertFalse("should not match", is(Integer.class).matches(new Object()));
        assertFalse("should not match", is(Integer.class).matches(null));
    }
}
