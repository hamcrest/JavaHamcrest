package org.hamcrest.collection;

import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;

import java.util.Arrays;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsEmptyIterableTest extends AbstractMatcherTest {

	@Override
	protected Matcher<?> createMatcher() {
		return emptyIterable();
	}

	public void testMatchesAnEmptyIterable() {
		assertMatches("empty iterable", emptyIterable(), Arrays.asList());
	}

	public void testDoesNotMatchAnIterableWithItems() {
		assertDoesNotMatch("iterable with an item", emptyIterable(), Arrays.<Object>asList(1));
	}

	public void testHasAReadableDescription() {
        assertDescription("is an empty iterable", emptyIterable());
	}
}
