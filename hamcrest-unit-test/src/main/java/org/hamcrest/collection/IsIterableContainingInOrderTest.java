package org.hamcrest.collection;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsIterableContainingInOrderTest extends AbstractMatcherTest {

	@Override
	protected Matcher<?> createMatcher() {
		return contains(1, 2);
	}
	
	public void testMatchingSingleItemIterable() throws Exception {
		assertMatches("Single item iterable", contains(1), asList(1));
	}
	
	public void testMatchingMultipleItemIterable() throws Exception {
		assertMatches("Multiple item iterable", contains(1, 2, 3), asList(1, 2, 3));
	}
	
	public void testDoesNotMatchWithMoreElementsThanExpected() throws Exception {
		assertDoesNotMatch("More elements than expected", contains(1, 2, 3), asList(1, 2, 3, 4));
	}
	
	public void testDoesNotMatchWithLessElementsThanExpected() throws Exception {
		assertDoesNotMatch("Less elements than expected", contains(1, 2, 3), asList(1, 2));
	}
	
	public void testDoesNotMatchIfSingleItemMismatches() throws Exception {
		assertDoesNotMatch("Single item iterable mismatches", contains(3), asList(2));	
	}
	
	public void testDoesNotMatchIfOneOfMultipleItemsMismatch() throws Exception {
		assertDoesNotMatch("Multiple item iterable mismatches", contains(1, 2, 3), asList(1, 2, 4));
	}
}
