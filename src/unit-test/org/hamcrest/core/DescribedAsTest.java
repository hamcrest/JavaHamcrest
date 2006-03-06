/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class DescribedAsTest extends AbstractMatcherTest {

    public void testOverridesDescriptionOfOtherMatcherWithThatPassedToConstructor() {
        Matcher m1 = new DescribedAs("m1 description", new Always(true, "always true"));
        Matcher m2 = new DescribedAs("m2 description", new Always(false, "always false"));

        assertDescription("m1 description", m1);
        assertDescription("m2 description", m2);
    }

    public void testDelegatesMatchingToAnotherMatcher() {
        Matcher m1 = new DescribedAs("irrelevant", new Always(true, "always true"));
        Matcher m2 = new DescribedAs("irrelevant", new Always(false, "always false"));

        assertTrue(m1.match(new Object()));
        assertFalse(m2.match(new Object()));
    }

}
