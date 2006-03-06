/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsEqualIgnoringWhiteSpaceTest extends AbstractMatcherTest {

    private final Matcher matcher = new IsEqualIgnoringWhiteSpace("Hello World   how\n are we? ");

    public void testPassesIfWordsAreSameButWhitespaceDiffers() {
        assertTrue(matcher.match("Hello World how are we?"));
        assertTrue(matcher.match("   Hello World   how are \n\n\twe?"));
    }

    public void testFailsIfTextOtherThanWhitespaceDiffers() {
        assertFalse(matcher.match("Hello PLANET how are we?"));
        assertFalse(matcher.match("Hello World how are we"));
    }

    public void testFailsIfWhitespaceIsAddedOrRemovedInMidWord() {
        assertFalse(matcher.match("HelloWorld how are we?"));
        assertFalse(matcher.match("Hello Wo rld how are we?"));
    }

    public void testFailsIfNotMatchingAgainstString() {
        assertFalse(matcher.match(new Object()));
    }

    public void testFailsIfMatchingAgainstNull() {
        assertFalse(matcher.match(null));
    }

    public void testRequiresNonNullStringToBeConstructed() {
        try {
            new IsEqualIgnoringWhiteSpace(null);
            fail("Expected exception");
        } catch (IllegalArgumentException goodException) {
            // expected!
        }
    }

    public void testDescribesItselfAsCaseInsensitive() {
        assertDescription("eqIgnoringWhiteSpace(\"Hello World   how\\n are we? \")", matcher);
    }
}
