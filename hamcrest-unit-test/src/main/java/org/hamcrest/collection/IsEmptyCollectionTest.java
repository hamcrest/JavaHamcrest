package org.hamcrest.collection;

import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.util.Arrays;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsEmptyCollectionTest extends AbstractMatcherTest {
	@Override
	protected Matcher<?> createMatcher() {
		return empty();
	}

	public void testMatchesAnEmptyCollection() throws Exception {
		assertMatches("empty collection", empty(), Arrays.asList());
	}

	public void testDoesNotMatchACollectionWithAnItem() throws Exception {
		assertDoesNotMatch("collection with an item", empty(), Arrays.<Object>asList(1));
	}
}
