package org.hamcrest.collection;

import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsArrayWithSizeTest extends AbstractMatcherTest {
	@Override
	protected Matcher<?> createMatcher() {
		return arrayWithSize(2);
	}

	public void testMatchesWhenSizeIsCorrect() throws Exception {
		assertMatches("correct size", arrayWithSize(3), new Object[] { 1, 2, 3});
	}

	public void testDoesNotMatchIncorrectSize() throws Exception {
		assertDoesNotMatch("incorrect size", arrayWithSize(2), new Object[] { 1, 2, 3});
	}
}
