package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.core.CharSequenceLength.*;

public class CharSequenceLengthTest extends AbstractMatcherTest {
    private final Matcher<CharSequence> matcher = length(4);

    @Override
    protected Matcher<?> createMatcher() {
        return length(4);
    }

    public void test_matchesIfArgumentIsEqualToADoubleValueWithinSomeError() {
        assertMatches(matcher, "aaaa");
        assertMatches(matcher, "a b ");

        assertDoesNotMatch("\"aaaaaa\" does not have length 4", matcher, "aaaaaa");
        assertMismatchDescription("\"aaaaaa\" does not have length <4>", matcher, "aaaaaa");
    }

}
