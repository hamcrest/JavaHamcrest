package org.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;

public final class CombinableTest {
    private static final CombinableMatcher<Integer> EITHER_3_OR_4 = CombinableMatcher.either(equalTo(3)).or(equalTo(4));
    private static final CombinableMatcher<Integer> NOT_3_AND_NOT_4 = CombinableMatcher.both(not(equalTo(3))).and(not(equalTo(4)));

    @Test public void
    copesWithNullsAndUnknownTypes() {
        assertNullSafe(EITHER_3_OR_4);
        assertNullSafe(NOT_3_AND_NOT_4);
    }

    @Test public void
    bothAcceptsAndRejects() {
        assertMatches("both didn't pass", NOT_3_AND_NOT_4, 2);
        assertDoesNotMatch("both didn't fail", NOT_3_AND_NOT_4, 3);
    }

    @Test public void
    acceptsAndRejectsThreeAnds() {
        CombinableMatcher<? super Integer> tripleAnd = NOT_3_AND_NOT_4.and(equalTo(2));
        
        assertMatches("tripleAnd didn't pass", tripleAnd, 2);
        assertDoesNotMatch("tripleAnd didn't fail", tripleAnd, 3);
    }

    @Test public void
    bothDescribesItself() {
        assertDescription("(not <3> and not <4>)", NOT_3_AND_NOT_4);
        assertMismatchDescription("not <3> was <3>", NOT_3_AND_NOT_4, 3);
    }

    @Test public void
    eitherAcceptsAndRejects() {
        assertMatches("either didn't pass", EITHER_3_OR_4, 3);
        assertDoesNotMatch("either didn't fail", EITHER_3_OR_4, 6);
    }

    @Test public void
    acceptsAndRejectsThreeOrs() {
        final CombinableMatcher<Integer> tripleOr = EITHER_3_OR_4.or(equalTo(11));
        
        assertMatches("tripleOr didn't pass", tripleOr, 11);
        assertDoesNotMatch("tripleOr didn't fail", tripleOr, 9);
    }

    @Test public void
    eitherDescribesItself() {
        assertDescription("(<3> or <4>)", EITHER_3_OR_4);
        assertMismatchDescription("was <6>", EITHER_3_OR_4, 6);
    }

    @Test public void
    picksUpTypeFromLeftHandSideOfExpression() {
        @SuppressWarnings("unused")
        Matcher<String> matcher = CombinableMatcher.both(equalTo("yellow")).and(notNullValue(String.class));
    }
}
