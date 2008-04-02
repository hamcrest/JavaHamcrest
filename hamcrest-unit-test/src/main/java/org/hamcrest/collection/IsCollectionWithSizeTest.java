package org.hamcrest.collection;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsCollectionWithSizeTest extends AbstractMatcherTest {
	@Override
	protected Matcher<?> createMatcher() {
		return hasSize(7);
	}

	public void testMatchesEqualSize() throws Exception {
		assertMatches("correct size", hasSize(2), asList(null, null));
	}

	public void testDoesNotMatcherDifferentSize() throws Exception {
		assertDoesNotMatch("incorrect size", hasSize(2), asList(null, null, null));
	}
}
