package org.hamcrest.collection;

import static org.hamcrest.collection.IsArrayContainingSingleItem.arrayWithSingleItem;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsArrayContainingSingleItemTest extends AbstractMatcherTest {

	@Override
	protected Matcher<?> createMatcher() {
		return arrayWithSingleItem(equalTo(1));
	}

	public void testHasAReadableDescription() {
        assertDescription("a singleton array with [<1>]",
                        arrayWithSingleItem(equalTo(1)));
	}
}
