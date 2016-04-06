package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static java.util.Arrays.asList;


public class StringContainsInOrderTest extends AbstractMatcherTest {
    StringContainsInOrder m = new StringContainsInOrder(asList("a", "b", "c", "c"));

    @Override
    protected Matcher<?> createMatcher() {
        return m;
    }
    
    public void testMatchesOnlyIfStringContainsGivenSubstringsInTheSameOrder() {
        assertMatches("substrings in order", m, "abcc");
        assertMatches("substrings separated", m, "1a2b3c4c5");
        
        assertDoesNotMatch("substrings in order missing a repeated pattern", m, "abc");
        assertDoesNotMatch("substrings out of order", m, "cab");
        assertDoesNotMatch("no substrings in string", m, "xyz");
        assertDoesNotMatch("substring missing", m, "ac");
        assertDoesNotMatch("empty string", m, "");
    }
    
    public void testHasAReadableDescription() {
        assertDescription("a string containing \"a\", \"b\", \"c\", \"c\" in order", m);
    }
}
