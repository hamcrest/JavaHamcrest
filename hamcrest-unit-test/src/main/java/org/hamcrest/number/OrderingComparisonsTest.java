/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.number.OrderingComparisons.*;

public class OrderingComparisonsTest extends AbstractMatcherTest {

    protected Matcher<?> createMatcher() {
        return greaterThan(1);
    }

    public void testComparesObjectsForGreaterThan() {
        assertThat(2, greaterThan(1));
        assertThat(0, not(greaterThan(1)));
    }

    public void testComparesObjectsForLessThan() {
        assertThat(2, lessThan(3));
        assertThat(0, lessThan(1));
    }

    public void testAllowsForInclusiveComparisons() {
        assertThat(1, lessThanOrEqualTo(1));
        assertThat(1, greaterThanOrEqualTo(1));
    }

    public void testSupportsDifferentTypesOfComparableObjects() {
        assertThat(1.1, greaterThan(1.0));

        assertThat("cc", greaterThan("bb"));
    }
}
