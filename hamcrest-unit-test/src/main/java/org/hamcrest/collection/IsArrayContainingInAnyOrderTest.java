package org.hamcrest.collection;

import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsArrayContainingInAnyOrderTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return arrayContainingInAnyOrder(equalTo(1), equalTo(2));
    }

    public void testHasAReadableDescription() {
        assertDescription("[<<1>>, <<2>>] in any order",
                            arrayContainingInAnyOrder(equalTo(1), equalTo(2)));
    }
}
