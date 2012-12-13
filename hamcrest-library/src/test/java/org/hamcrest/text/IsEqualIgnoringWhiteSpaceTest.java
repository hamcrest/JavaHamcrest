/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsEqualIgnoringWhiteSpaceTest extends AbstractMatcherTest {

    private final Matcher<String> matcher = equalToIgnoringWhiteSpace("Hello World   how\n are we? ");

    @Override
    protected Matcher<?> createMatcher() {
        return matcher;
    }

    public void testPassesIfWordsAreSameButWhitespaceDiffers() {
        assertThat("Hello World how are we?", matcher);
        assertThat("   Hello World   how are \n\n\twe?", matcher);
    }

    public void testFailsIfTextOtherThanWhitespaceDiffers() {
        assertThat("Hello PLANET how are we?", not(matcher));
        assertThat("Hello World how are we", not(matcher));
    }

    public void testFailsIfWhitespaceIsAddedOrRemovedInMidWord() {
        assertThat("HelloWorld how are we?", not(matcher));
        assertThat("Hello Wo rld how are we?", not(matcher));
    }

    public void testFailsIfMatchingAgainstNull() {
        assertThat(null, not(matcher));
    }

    public void testRequiresNonNullStringToBeConstructed() {
        try {
            new IsEqualIgnoringWhiteSpace(null);
            fail("Expected exception");
        } catch (IllegalArgumentException goodException) {
            // expected!
        }
    }

    public void testHasAReadableDescription() {
        assertDescription("equalToIgnoringWhiteSpace(\"Hello World   how\\n are we? \")",
                        matcher);
    }
}
