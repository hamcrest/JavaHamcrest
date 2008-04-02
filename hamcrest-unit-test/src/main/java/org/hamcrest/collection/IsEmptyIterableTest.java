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

	public void testMatchesAnEmtpyIterable() throws Exception {
		assertMatches("empty iterable", emptyIterable(), Arrays.asList());
	}

	public void testDoesNotMatchAnIterableWithItems() throws Exception {
		assertDoesNotMatch("iterable with an item", emptyIterable(), Arrays.<Object>asList(1));
	}
}
