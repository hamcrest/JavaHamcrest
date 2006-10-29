/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEqualIgnoringCase.eqIgnoringCase;

public class IsEqualIgnoringCaseTest extends AbstractMatcherTest {

    protected Matcher<?> createMatcher() {
        return eqIgnoringCase("irrelevant");
    }

    public void testIgnoresCaseOfCharsInString() {
        assertThat("HELLO", eqIgnoringCase("heLLo"));
        assertThat("hello", eqIgnoringCase("heLLo"));
        assertThat("HelLo", eqIgnoringCase("heLLo"));

        assertThat("bye", not(eqIgnoringCase("heLLo")));
    }

    public void testFailsIfAdditionalWhitespaceIsPresent() {
        assertThat("heLLo ", not(eqIgnoringCase("heLLo")));
        assertThat(" heLLo", not(eqIgnoringCase("heLLo")));
    }

    public void testFailsIfMatchingAgainstNull() {
        assertThat(null, not(eqIgnoringCase("heLLo")));
    }

    public void testRequiresNonNullStringToBeConstructed() {
        try {
            eqIgnoringCase(null);
            fail("Expected exception");
        } catch (IllegalArgumentException goodException) {
            // expected!
        }
    }

    public void testDescribesItselfAsCaseInsensitive() {
        assertDescription("eqIgnoringCase(\"heLLo\")", eqIgnoringCase("heLLo"));
    }
}
