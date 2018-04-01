package org.hamcrest;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.junit.Assert.assertEquals;

public final class FeatureMatcherTest {
    private final FeatureMatcher<Thingy, String> resultMatcher = resultMatcher();

    @Test public void
    matchesPartOfAnObject() {
        assertMatches("feature", resultMatcher, new Thingy("bar"));
        assertDescription("Thingy with result \"bar\"", resultMatcher);
    }

    @Test public void
    mismatchesPartOfAnObject() {
        assertMismatchDescription("result mismatch-description", resultMatcher, new Thingy("foo"));
    }

    @Test public void
    doesNotThrowNullPointerException() {
        assertMismatchDescription("was null", resultMatcher, null);
    }

    @Test public void
    doesNotThrowClassCastException() {
        resultMatcher.matches(new ShouldNotMatch());
        StringDescription mismatchDescription = new StringDescription(); 
        resultMatcher.describeMismatch(new ShouldNotMatch(), mismatchDescription);
        assertEquals("was ShouldNotMatch <ShouldNotMatch>", mismatchDescription.toString());
    }

    public static class Match extends IsEqual<String> {
        public Match(String equalArg) { super(equalArg); }
        @Override public void describeMismatch(String item, Description description) {
            description.appendText("mismatch-description");
        }
    }

    public static class Thingy {
        private final String result;

        public Thingy(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }

    public static class ShouldNotMatch {
        @Override public String toString() { return "ShouldNotMatch"; }
    } 

    private static FeatureMatcher<Thingy, String> resultMatcher() {
        return new FeatureMatcher<Thingy, String>(new Match("bar"), "Thingy with result", "result") {
            @Override
            public String featureValueOf(Thingy actual) {
                return actual.getResult();
            }
        };
    }

}
