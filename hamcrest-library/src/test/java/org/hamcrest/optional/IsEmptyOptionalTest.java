package org.hamcrest.optional;


import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.optional.IsEmptyOptional.*;

public class IsEmptyOptionalTest {

    private static Optional<Integer> EMPTY_OPTIONAL = Optional.empty();
    private static Optional<Integer> NON_EMPTY_OPTIONAL = Optional.of(1);
    private static Optional<Integer> NULL_EMPTY_OPTIONAL = Optional.ofNullable(null);

    @Test
    public void copesWithNullsAndUnknownTypes(){
        Matcher<Optional<Integer>> matcher = emptyOptional();
        matcher.matches(null);

        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }


    @Test
    public void matchWhenUtilisingANestedMatcher(){
        Matcher<Optional<Integer>> matcher = is(emptyOptional());

        assertMatches(matcher, EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, NON_EMPTY_OPTIONAL);
    }


    @Test
    public void matchWhenReceiveAEmptyOptional(){
        Matcher<Optional<Integer>> matcher = emptyOptional();

        assertMatches(matcher, EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, NON_EMPTY_OPTIONAL);
    }

    @Test
    public void matchWhenReceiveAOptionalWithNullValue(){
        Matcher<Optional<Integer>> matcher = emptyOptional();

        assertMatches(matcher, NULL_EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, NON_EMPTY_OPTIONAL);
    }


    @Test
    public void describeItSelf(){
        Matcher<Optional<Integer>> matcher = emptyOptional();

        assertDescription("Optional<Empty>", matcher);
    }


    @Test
    public void describesAMismatch(){

        Matcher<Optional<Integer>> matcher = emptyOptional();

        assertMismatchDescription("was Optional<1>", matcher, NON_EMPTY_OPTIONAL);

    }
}
