package org.hamcrest.number;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsCloseToTest extends AbstractMatcherTest {

  private final Matcher<Number> matcher = closeTo(1.0d, 0.5d);

  @Override
    protected Matcher<?> createMatcher() {
        final double irrelevant = 0.1;
        return closeTo(irrelevant, irrelevant);
    }

    @Test
    public void test_matchesIfArgumentIsEqualToADoubleValueWithinSomeError() {
        assertMatches("1.0", matcher, 1.0);
        assertMatches("0.5d", matcher, 0.5d);
        assertMatches("1.5d", matcher, 1.5d);

        assertDoesNotMatch("too large", matcher, 2.0);
        assertMismatchDescription("<3.0> differed by <1.5> more than delta <0.5>", matcher, 3.0d);
        assertDoesNotMatch("number too small", matcher, 0.0);
        assertMismatchDescription("<0.1> differed by <0.4> more than delta <0.5>", matcher, 0.1);
    }

    @Test
    public void test_matchesIfArgumentIsEqualToAFloatValueWithinSomeError() {
        assertMatches("1.0f", matcher, 1.0f);
        assertMatches("0.5f", matcher, 0.5f);
        assertMatches("1.5f", matcher, 1.5f);

        assertDoesNotMatch("too large", matcher, 2.0f);
        assertMismatchDescription("<3.0F> differed by <1.5> more than delta <0.5>", matcher, 3.0f);
        assertDoesNotMatch("number too small", matcher, 0.0f);
        assertMismatchDescription("<0.0F> differed by <0.5> more than delta <0.5>", matcher, 0.0f);
    }

    @Test
    public void test_matchesIfArgumentIsEqualToAnIntegerValueWithinSomeError() {
        assertMatches("1", matcher, 1);
        assertDoesNotMatch("too large", matcher, 2);
        assertMismatchDescription("<3> differed by <1.5> more than delta <0.5>", matcher, 3);
        assertDoesNotMatch("number too small", matcher, 0);
        assertMismatchDescription("<0> differed by <0.5> more than delta <0.5>", matcher, 0);
    }

    @Test
    public void test_matchesIfArgumentIsEqualToALongValueWithinSomeError() {
        assertMatches("1L", matcher, 1L);
        assertDoesNotMatch("too large", matcher, 2L);
        assertMismatchDescription("<3L> differed by <1.5> more than delta <0.5>", matcher, 3L);
        assertDoesNotMatch("number too small", matcher, 0L);
        assertMismatchDescription("<0L> differed by <0.5> more than delta <0.5>", matcher, 0L);
    }

    /**
     * Regression for #444: float bean properties failed when closeTo was used with
     * hasProperty / is chaining because TypeSafeMatcher only accepted Double.
     */
    @Test
    public void test_matchesFloatPropertyWhenChainedWithHasProperty() {
        final BeanWithFloatNumber subject = new BeanWithFloatNumber(1.0f);

        assertMatches(hasProperty("number", closeTo(0.99, 0.1)), subject);
        assertMatches(hasProperty("number", is(closeTo(0.99, 0.1))), subject);
        assertDoesNotMatch(hasProperty("number", closeTo(0.5, 0.1)), subject);
        // Must report numeric distance, not "was a java.lang.Float" type rejection (#444).
        final String mismatch = mismatchDescription(hasProperty("number", closeTo(0.5, 0.1)), subject);
        assertTrue(mismatch.contains("differed by"), "unexpected mismatch: " + mismatch);
        assertTrue(!mismatch.contains("was a java.lang.Float"), "unexpected type rejection: " + mismatch);
    }

    @Test
    public void test_is_self_describing() {
        assertDescription("a numeric value within <0.5> of <1.0>", matcher);
    }

    public static final class BeanWithFloatNumber {
        private final float number;

        public BeanWithFloatNumber(float number) {
            this.number = number;
        }

        public float getNumber() {
            return number;
        }
    }

}

