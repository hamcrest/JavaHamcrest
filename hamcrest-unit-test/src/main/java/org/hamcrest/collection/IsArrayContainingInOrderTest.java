package org.hamcrest.collection;

import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsArrayContainingInOrderTest extends AbstractMatcherTest {

	@Override
	protected Matcher<?> createMatcher() {
		return arrayContaining(equalTo(1), equalTo(2));
	}

	public void testHasAReadableDescription() {
        assertDescription("[<1>, <2>]",
                            arrayContaining(equalTo(1), equalTo(2)));
	}
}
