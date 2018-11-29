package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.hamcrest.text.CharSequenceLength.hasLength;

/**
 * @author Marco Leichsenring
 * @author Steve Freeman
 */
public class CharSequenceLengthTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasLength(4);
    }

    public void test_matchesExactLengthOf_CharSequence() {
        final Matcher<CharSequence> matcher = hasLength(4);
        assertMatches(matcher, "aaaa");
        assertMatches(matcher, "a b ");

        assertMismatchDescription("length was <6>", matcher, "aaaaaa");
    }


    public void test_matchesRelativeLengthOf_CharSequence() {
        final Matcher<CharSequence> matcher = hasLength(lessThan(4));
        assertMatches(matcher, "aaa");
        assertMatches(matcher, "a b");

        assertMismatchDescription("length <4> was equal to <4>", matcher, "aaaa");
        assertMismatchDescription("length <5> was greater than <4>", matcher, "aaaaa");
    }

}
