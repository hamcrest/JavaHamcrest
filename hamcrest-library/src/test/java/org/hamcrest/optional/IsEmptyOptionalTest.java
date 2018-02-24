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

    @Test
    public void copesWithNullsAndUnknownTypes(){
        Matcher<Optional<?>> matcher = emptyOptional();
        matcher.matches(null);

        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }


    @Test
    public void matchWhenUtilisingANestedMatcher(){
        Matcher<Optional<?>> matcher = is(emptyOptional());

        assertMatches(matcher, EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, NON_EMPTY_OPTIONAL);
    }


    @Test
    public void matchWhenReceiveAEmptyOptional(){
        Matcher<Optional<?>> matcher = emptyOptional();

        assertMatches(matcher, EMPTY_OPTIONAL);
        assertDoesNotMatch(matcher, NON_EMPTY_OPTIONAL);
    }


    @Test
    public void describeItSelf(){
        Matcher<Optional<?>> matcher = emptyOptional();

        assertDescription("<Optional.empty>", matcher);
    }


    @Test
    public void describesAMismatch(){

        Matcher<Optional<?>> matcher = emptyOptional();

        assertMismatchDescription("was <Optional[1]>", matcher, NON_EMPTY_OPTIONAL);

    }
}
