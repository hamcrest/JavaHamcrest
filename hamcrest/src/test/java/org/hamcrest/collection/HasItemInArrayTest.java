package org.hamcrest.collection;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.collection.ArrayMatching.hasItemInArray;

public class HasItemInArrayTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasItemInArray("irrelevant");
    }

    @Test
    public void testMatchesAnArrayThatContainsAnElementMatchingTheGivenMatcher() {
        assertMatches("should matches array that contains 'a'",
                hasItemInArray("a"), new String[]{"a", "b", "c"});
    }

    @Test
    public void testDoesNotMatchAnArrayThatDoesntContainAnElementMatchingTheGivenMatcher() {
        assertDoesNotMatch("should not matches array that doesn't contain 'a'",
                hasItemInArray("a"), new String[]{"b", "c"});
        assertDoesNotMatch("should not matches empty array",
                hasItemInArray("a"), new String[0]);
        assertMismatchDescription(
              "mismatches were: [<3> was greater than <2>, <4> was greater than <2>, <5> was greater than <2>]",
              hasItemInArray(lessThan(2)), new Integer[] {3, 4, 5});
    }

    @Test
    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not matches null",
                hasItemInArray("a"), null);
    }

    @Test
    public void testHasAReadableDescription() {
        assertDescription("an array containing a value less than <2>", hasItemInArray(lessThan(2)));
    }
}
