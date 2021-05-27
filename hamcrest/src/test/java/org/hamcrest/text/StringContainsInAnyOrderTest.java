package org.hamcrest.text;

import static java.util.Arrays.asList;
import static org.hamcrest.text.StringContainsInAnyOrder.stringContainsInAnyOrder;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;


public class StringContainsInAnyOrderTest extends AbstractMatcherTest {
    final StringContainsInAnyOrder matcher = new StringContainsInAnyOrder(asList("a", "b", "c"));

    @Override
    protected Matcher<?> createMatcher() {
        return matcher;
    }
    
    public void testMatchesOnlyIfStringContainsGivenSubstringsInTheSameOrder() {
        assertMatches("substrings in order", matcher, "abcccccc");
        assertMatches("substrings out of order", matcher, "cab");
        assertMatches("substrings separated", matcher, "1c2a3b");

        assertDoesNotMatch("no substrings in string", matcher, "xyz");
        assertDoesNotMatch("substring missing", matcher, "ac");
        assertDoesNotMatch("empty string", matcher, "");
    }
    
    public void testHasAReadableDescription() {
        assertDescription("a string containing \"a\", \"b\", \"c\" in any order", matcher);
    }
}
