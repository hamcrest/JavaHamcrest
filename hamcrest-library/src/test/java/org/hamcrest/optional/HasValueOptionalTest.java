package org.hamcrest.optional;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.optional.HasValueOptional.hasValue;

public class HasValueOptionalTest {
    private static Optional<Integer> EMPTY_OPTIONAL = Optional.empty();
    private static Optional<Integer> NON_EMPTY_OPTIONAL = Optional.of(1);
    private static Optional<Integer> NULL_EMPTY_OPTIONAL = Optional.ofNullable(null);

    @Test
    public void copesWithNullsAndUnknownTypes(){
        Matcher<Optional<Integer>> matcher = hasValue();
        matcher.matches(null);

        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test
    public void matchWhenReceiveAOptionalWithSomeValue(){
        Matcher<Optional<Integer>> matcher = hasValue();

        assertMatches(matcher, NON_EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, NULL_EMPTY_OPTIONAL);
    }


    @Test
    public void matchWhenUtilisingANestedMatcher(){
        Matcher<Optional<Integer>> matcher = is(hasValue());

        assertMatches(matcher, NON_EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, NULL_EMPTY_OPTIONAL);
    }


    @Test
    public void matchWhenReceiveAOptionalWithSpecificValue(){
        Matcher<Optional<Integer>> matcher = hasValue(1);

        assertMatches(matcher, NON_EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, NULL_EMPTY_OPTIONAL);
    }

    @Test
    public void noMatchWhenReceiveAOptionalWithADifferentValue(){
        Matcher<Optional<Integer>> matcher = hasValue(2);

        assertDoesNotMatch(matcher, NON_EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, NULL_EMPTY_OPTIONAL);
    }


    @Test(expected = IllegalArgumentException.class)
    public void throwsIllegalArgumentExceptionIfGivenNullValue(){
        hasValue(null);
    }

    @Test
    public void describeItSelf(){
        Matcher<Optional<Integer>> matcher = hasValue();

        assertDescription("Optional<Some Value>", matcher);
    }


    @Test
    public void describesAMismatch(){

        Matcher<Optional<Integer>> matcher = hasValue(2);

        assertMismatchDescription("was Optional<1>", matcher, NON_EMPTY_OPTIONAL);

    }
}
