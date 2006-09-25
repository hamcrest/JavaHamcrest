/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import static org.hamcrest.core.Always.alwaysFails;
import static org.hamcrest.core.Always.alwaysPasses;
import static org.hamcrest.core.DescribedAs.describedAs;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class DescribedAsTest extends AbstractMatcherTest {
    public void testOverridesDescriptionOfOtherMatcherWithThatPassedToConstructor() {
        Matcher m1 = describedAs("m1 description", alwaysPasses());
        Matcher m2 = describedAs("m2 description", alwaysFails());

        assertDescription("m1 description", m1);
        assertDescription("m2 description", m2);
    }

    public void testAppendsValuesToDescription() {
        Matcher m = describedAs("value 1 = %0, value 2 = %1",
                                alwaysPasses(), 33, 97);
        
        assertDescription("value 1 = <33>, value 2 = <97>", m);
    }
    
    public void testDelegatesMatchingToAnotherMatcher() {
        Matcher<Object> m1 = describedAs("irrelevant", alwaysPasses());
        Matcher<Object> m2 = describedAs("irrelevant", alwaysFails());

        assertTrue(m1.match(new Object()));
        assertFalse(m2.match("hi"));
    }
}
