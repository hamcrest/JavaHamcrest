package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static java.util.Arrays.asList;


public class StringContainsInAnyOrderTest extends AbstractMatcherTest {
    final StringContainsInAnyOrder matcher = new StringContainsInAnyOrder(asList("a", "b", "c"));

    @Override
    protected Matcher<?> createMatcher() {
        return matcher;
    }
    
    public void testMatchesEvenIfStringContainsGivenSubstringsInAnotherOrder() {
        assertMatches("substrings in any order", matcher, "abcc");
        assertMatches("substrings in any order", matcher, "cba");
        assertMatches("substrings separated", matcher, "1c2a3b4c5");

        assertDoesNotMatch("no substrings in string", matcher, "xyz");
        assertDoesNotMatch("substring missing", matcher, "ac");
        assertDoesNotMatch("empty string", matcher, "");
    }
    
    public void testHasAReadableDescription() {
        assertDescription("a string containing \"a\", \"b\", \"c\" in any order", matcher);
    }
}
