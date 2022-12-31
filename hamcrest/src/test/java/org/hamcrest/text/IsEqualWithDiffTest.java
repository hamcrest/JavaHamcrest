package org.hamcrest.text;

import org.hamcrest.Matcher;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.text.IsEqualWithDiff.equalToWithDiff;


public class IsEqualWithDiffTest {
    /*
    Test the correctness of diff message
     */
    @Test public void
    testMismatchesWithCorrectDiffMessage() {
        final String actual = "This is first see you, bye\nhbha";
        final Description expectedDescription = new StringDescription()
                .appendText("Diff: \n\t")
                .appendText("At line 0: This is first ~see~**hello** ~you~**world**, bye\n\t")
                .appendText("At line 1: ~hbha~**haha**\n\t");
        Description actualDescription = new StringDescription();
        final Matcher<String> matcher = equalToWithDiff("This is first hello world, bye\nhaha");
        matcher.describeMismatch(actual, actualDescription);

        assertMatches(equalToWithDiff(expectedDescription.toString()), actualDescription.toString());
    }

    /*
    Test the correctness of Matcher itself
     */
    @Test public void
    testMatcherCorrectness() {
        final String actual = "This is first see you, bye\nhbha";
        final String expected = "This is first see you, bye\nhbha";

        assertMatches(equalToWithDiff(expected), actual);
    }
}
