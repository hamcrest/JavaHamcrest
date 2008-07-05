package org.hamcrest.collection;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsCollectionWithSizeTest extends AbstractMatcherTest {

	@Override
	protected Matcher<?> createMatcher() {
		return hasSize(7);
	}

	public void testMatchesWhenSizeIsCorrect() {
		assertMatches("correct size", hasSize(equalTo(2)), asList(null, null));
		assertDoesNotMatch("incorrect size", hasSize(equalTo(2)), asList(null, null, null));
	}

	public void testProvidesConvenientShortcutForHasSizeEqualTo() {
		assertMatches("correct size", hasSize(2), asList(null, null));
		assertDoesNotMatch("incorrect size", hasSize(2), asList(null, null, null));
	}

	public void testHasAReadableDescription() {
        assertDescription("a collection with size <3>", hasSize(equalTo(3)));
	}
}
