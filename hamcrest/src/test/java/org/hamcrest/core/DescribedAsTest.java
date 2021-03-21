package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.core.DescribedAs.describedAs;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;

public final class DescribedAsTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<Object> matcher = describedAs("irrelevant", anything());

        assertNullSafe(matcher);
    }

    @Test public void
    overridesDescriptionOfOtherMatcherWithThatPassedToConstructor() {
        Matcher<?> matcher = describedAs("my description", anything());

        assertDescription("my description", matcher);
    }

    @Test public void
    appendsValuesToDescription() {
        Matcher<?> matcher = describedAs("value 1 = %0, value 2 = %1", anything(), 33, 97);

        assertDescription("value 1 = <33>, value 2 = <97>", matcher);
    }

    @Test public void
    celegatesMatchingToAnotherMatcher() {
        Matcher<String> matcher = describedAs("irrelevant", equalTo("hi"));

        assertMatches(matcher, "hi");
        assertDoesNotMatch("matched", matcher, "oi");
    }

    @Test public void
    delegatesMismatchDescriptionToAnotherMatcher() {
        Matcher<Integer> matcher = describedAs("irrelevant", equalTo(2));

        assertMismatchDescription("was <1>", matcher, 1);
    }
}
