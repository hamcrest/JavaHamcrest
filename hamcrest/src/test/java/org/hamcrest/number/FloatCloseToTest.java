package org.hamcrest.number;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.number.FloatCloseTo.closeTo;

public class FloatCloseToTest extends AbstractMatcherTest {

    private final Matcher<Float> matcher = closeTo(1.0f, 0.5f);

    @Override
    protected Matcher<?> createMatcher() {
        final float irrelevant = 0.1f;
        return closeTo(irrelevant, irrelevant);
    }

    public void test_matchesIfArgumentIsEqualToAFloatValueWithinSomeError() {
        assertMatches("1.0f", matcher, 1.0f);
        assertMatches("0.5f", matcher, 0.5f);
        assertMatches("1.5f", matcher, 1.5f);

        assertDoesNotMatch("too large", matcher, 2.0f);
        assertMismatchDescription("<3.0F> differed from <1.0F> by <2.0F> but allowed delta is less than <0.5F>", matcher, 3.0f);
        assertDoesNotMatch("number too small", matcher, 0.0f);
        assertMismatchDescription("<0.1F> differed from <1.0F> by <0.9F> but allowed delta is less than <0.5F>", matcher, 0.1f);
    }

    public void test_is_self_describing() {
        assertDescription("a numeric value within <0.5F> of <1.0F>", matcher);
    }

}
