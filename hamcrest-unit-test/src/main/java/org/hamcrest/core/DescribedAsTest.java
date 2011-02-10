/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import static org.hamcrest.core.DescribedAs.describedAs;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class DescribedAsTest extends AbstractMatcherTest {
    @Override
    protected Matcher<?> createMatcher() {
        return describedAs("irrelevant", anything());
    }

    public void testOverridesDescriptionOfOtherMatcherWithThatPassedToConstructor() {
        Matcher<?> m1 = describedAs("m1 description", anything());
        Matcher<?> m2 = describedAs("m2 description", not(anything()));

        assertDescription("m1 description", m1);
        assertDescription("m2 description", m2);
    }

    public void testAppendsValuesToDescription() {
        Matcher<?> m = describedAs("value 1 = %0, value 2 = %1",
                anything(), 33, 97);

        assertDescription("value 1 = <33>, value 2 = <97>", m);
    }

    public void testDelegatesMatchingToAnotherMatcher() {
        Matcher<Object> m1 = describedAs("irrelevant", anything());
        Matcher<Object> m2 = describedAs("irrelevant", not(anything()));

        assertTrue(m1.matches(new Object()));
        assertFalse(m2.matches("hi"));
    }

    public void testDelegatesMismatchDescriptionToAnotherMatcher() {
        Matcher<Integer> m1 = describedAs("irrelevant", equalTo(2));

        StringDescription description = new StringDescription();
        m1.describeMismatch(1, description);

        assertEquals("was <1>", description.toString());
    }
}
