package org.hamcrest.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public final class MatcherMatcherTest {

    private Matcher<?> matcherMatcher;

    private final Matcher<String> dummyNotNullValueMatcher = new BaseMatcher<String>() {
        @Override
        public boolean matches(Object item) {
            return item != null;
        }
        
        @Override
        public void describeMismatch(Object item, Description description) {
            description.appendText("the value was null");
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("a non-null value");
        }
    };

    @Test public void
    describesItselfWhenConfiguredToMatchOnTheDescriptionOfTheAssessedMatcher() {
        matcherMatcher = MatcherMatcher.aMatcherWithDescription(equalTo("matcher description"));
        
        assertThat(matcherDescription(), is("a Matcher with description \"matcher description\""));
    }

    @Test public void
    describesItselfWhenConfiguredToMatchOnTheSuccessfulMatchingOfTheAssessedMatcher() {
        matcherMatcher = MatcherMatcher.aMatcherThatMatches("Apples");
        
        assertThat(matcherDescription(), is("a Matcher that matches \"Apples\""));
    }

    @Test public void
    describesItselfWhenConfiguredToMatchOnTheMismatchDescriptionProvidedByTheAssessedMatcher() {
        matcherMatcher = MatcherMatcher.aMatcherGivingAMismatchDescriptionOf("Apples", equalTo("was Bananas"));
        
        assertThat(matcherDescription(),
                   is("a Matcher that does not match \"Apples\" and gives a mismatch description of \"was Bananas\""));
    }

    @Test public void
    matchesTheDescriptionOfTheAssessedMatcher() {
        matcherMatcher = MatcherMatcher.aMatcherWithDescription(equalTo("rubbish"));
        assertThat(matcherMatcher.matches(dummyNotNullValueMatcher), is(false));
        
        matcherMatcher = MatcherMatcher.aMatcherWithDescription(equalTo("a non-null value"));
        assertThat(matcherMatcher.matches(dummyNotNullValueMatcher), is(true));
    }

    @Test public void
    matchesTheMatchingFunctionalityOfTheAssessedMatcher() {
        matcherMatcher = MatcherMatcher.aMatcherThatMatches("Apples");
        assertThat(matcherMatcher.matches(dummyNotNullValueMatcher), is(true));
        
        matcherMatcher = MatcherMatcher.aMatcherThatMatches(null);
        assertThat(matcherMatcher.matches(dummyNotNullValueMatcher), is(false));
    }

    @Test public void
    matchesTheMismatchDescriptionOfTheAssessedMatcher() {
        matcherMatcher = MatcherMatcher.aMatcherGivingAMismatchDescriptionOf(null, equalTo("the value was null"));
        assertThat(matcherMatcher.matches(dummyNotNullValueMatcher), is(true));
        
        matcherMatcher = MatcherMatcher.aMatcherGivingAMismatchDescriptionOf("", equalTo("the value was null"));
        assertThat(matcherMatcher.matches(dummyNotNullValueMatcher), is(false));
        
        matcherMatcher = MatcherMatcher.aMatcherGivingAMismatchDescriptionOf(null, equalTo("the value was spaghetti"));
        assertThat(matcherMatcher.matches(dummyNotNullValueMatcher), is(false));
    }

    @Test public void
    reportsMismatchInTheDesctiptionOfTheAssessedMatcher() {
        matcherMatcher = MatcherMatcher.aMatcherWithDescription(equalTo("rubbish"));
        assertThat(mismatchDescriptionOf(dummyNotNullValueMatcher),
                   is("was a Matcher whose description was \"a non-null value\""));
    }

    @Test public void
    reportsMismatchInTheMatchingFunctionalityOfTheAssessedMatcher() {
        matcherMatcher = MatcherMatcher.aMatcherThatMatches(null);
        assertThat(mismatchDescriptionOf(dummyNotNullValueMatcher),
                   is("was a Matcher that did not match, and instead gave a mismatch description of \"the value was null\""));
    }
    
    @Test public void
    reportsMismatchInTheMismatchDescriptionOfTheAssessedMatcher() {
        matcherMatcher = MatcherMatcher.aMatcherGivingAMismatchDescriptionOf("", equalTo("the value was null"));
        assertThat(mismatchDescriptionOf(dummyNotNullValueMatcher), is("was a Matcher that matched."));
        
        matcherMatcher = MatcherMatcher.aMatcherGivingAMismatchDescriptionOf(null, equalTo("the value was spaghetti"));
        assertThat(mismatchDescriptionOf(dummyNotNullValueMatcher), is("was a Matcher whose mismatch description was \"the value was null\""));
    }

    private String matcherDescription() {
        StringDescription description = new StringDescription();
        matcherMatcher.describeTo(description);
        return description.toString();
    }

    private String mismatchDescriptionOf(Object value) {
        StringDescription description = new StringDescription();
        matcherMatcher.describeMismatch(value, description);
        return description.toString();
    }
}
