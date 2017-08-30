package org.hamcrest.number;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.number.IsCloseTo.closeTo;

public class IsCloseToTest extends AbstractMatcherTest {
  private final Matcher<Number> matcher = closeTo(1.0d, 0.5d);

  @Override
    protected Matcher<?> createMatcher() {
        final double irrelevant = 0.1;
        return closeTo(irrelevant, irrelevant);
    }

    public void test_matchesIfArgumentIsEqualToADoubleValueWithinSomeError() {
        assertMatches("1.0", matcher, 1.0);
        assertMatches("0.5d", matcher, 0.5d);
        assertMatches("1.5d", matcher, 1.5d);

        assertDoesNotMatch("too large", matcher, 2.0);
        assertMismatchDescription("<3.0> differed by <1.5> more than delta <0.5>", matcher, 3.0d);
        assertDoesNotMatch("number too small", matcher, 0.0);
        assertMismatchDescription("<0.1> differed by <0.4> more than delta <0.5>", matcher, 0.1);
    }

    public void test_matchesIfArgumentIsEqualToAFloatValueWithinSomeError() {
        assertMatches("1.0", matcher, 1.0f);
        assertMatches("0.5d", matcher, 0.5f);
        assertMatches("1.5d", matcher, 1.5f);

        assertDoesNotMatch("too large", matcher, 2.0f);
        String description = mismatchDescription(matcher, 3.0f);
        assertTrue(description.matches("<3\\.0F> differed by <1\\.(499\\d+|5|500\\d+)> more than delta <0\\.5>"));
        assertDoesNotMatch("number too small", matcher, 0.1f);
        description = mismatchDescription(matcher, 0.1f);
        assertTrue(description.matches("<0\\.1F> differed by <0\\.(399\\d+|4|400\\d+)> more than delta <0\\.5>"));
    }

    public void test_matchesIfArgumentIsEqualToALongValueWithinSomeError() {
        assertMatches("1.0", matcher, 1L);

        assertDoesNotMatch("too large", matcher, 2L);
        assertMismatchDescription("<3L> differed by <1.5> more than delta <0.5>", matcher, 3L);
        assertDoesNotMatch("number too small", matcher, 0L);
        assertMismatchDescription("<0L> differed by <0.5> more than delta <0.5>", matcher, 0L);
    }

    public void test_matchesIfArgumentIsEqualToAnIntegerValueWithinSomeError() {
        assertMatches("1.0", matcher, 1);

        assertDoesNotMatch("too large", matcher, 2);
        assertMismatchDescription("<3> differed by <1.5> more than delta <0.5>", matcher, 3);
        assertDoesNotMatch("number too small", matcher, 0);
        assertMismatchDescription("<0> differed by <0.5> more than delta <0.5>", matcher, 0);
    }

    public void test_matchesIfArgumentIsEqualToAShortValueWithinSomeError() {
        assertMatches("1.0", matcher, (short) 1);

        assertDoesNotMatch("too large", matcher, (short) 2);
        assertMismatchDescription("<3s> differed by <1.5> more than delta <0.5>", matcher, (short) 3);
        assertDoesNotMatch("number too small", matcher, (short) 0);
        assertMismatchDescription("<0s> differed by <0.5> more than delta <0.5>", matcher, (short) 0);
    }

    public void test_is_self_describing() {
        assertDescription("a numeric value within <0.5> of <1.0>", matcher);
    }

}
