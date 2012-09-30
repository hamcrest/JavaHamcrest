package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.core.Are.are;
import static org.hamcrest.core.IsEqual.equalTo;

public class AreTest extends AbstractMatcherTest {
    @Override
    protected Matcher<?> createMatcher() {
        return are("something");
    }

    public void testJustMatchesTheSameWayTheUnderylingMatcherDoes() {
        assertMatches("should match", are(equalTo(true)), true);
        assertMatches("should match", are(equalTo(false)), false);
        assertDoesNotMatch("should not match", are(equalTo(true)), false);
        assertDoesNotMatch("should not match", are(equalTo(false)), true);
    }

    public void testGeneratesIsPrefixInDescription() {
        assertDescription("are <true>", are(equalTo(true)));
    }

    public void testProvidesConvenientShortcutForIsEqualTo() {
        assertMatches("should match", are("A"), "A");
        assertMatches("should match", are("B"), "B");
        assertDoesNotMatch("should not match", are("A"), "B");
        assertDoesNotMatch("should not match", are("B"), "A");
        assertDescription("are \"A\"", are("A"));
    }
}
