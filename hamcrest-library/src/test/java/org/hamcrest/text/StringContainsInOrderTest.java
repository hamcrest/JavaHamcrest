package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static java.util.Arrays.asList;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;


public class StringContainsInOrderTest extends AbstractMatcherTest {
    final StringContainsInOrder matcher = new StringContainsInOrder(asList("a", "b", "c", "c"));

    @Override
    protected Matcher<?> createMatcher() {
        return matcher;
    }
    
    public void testMatchesOnlyIfStringContainsGivenSubstringsInTheSameOrder() {
        assertMatches("substrings in order", matcher, "abcc");
        assertMatches("substrings separated", matcher, "1a2b3c4c5");

        assertDoesNotMatch("can detect repeated strings for matching", stringContainsInOrder("abc", "abc"), "---abc---");
        assertDoesNotMatch("substrings in order missing a repeated pattern", matcher, "abc");
        assertDoesNotMatch("substrings out of order", matcher, "cab");
        assertDoesNotMatch("no substrings in string", matcher, "xyz");
        assertDoesNotMatch("substring missing", matcher, "ac");
        assertDoesNotMatch("empty string", matcher, "");
    }
    
    public void testHasAReadableDescription() {
        assertDescription("a string containing \"a\", \"b\", \"c\", \"c\" in order", matcher);
    }
}
