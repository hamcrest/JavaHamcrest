package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.collection.IsArrayOnlyContaining.isArrayOnlyContaining;

public class IsArrayOnlyContainingTest extends AbstractMatcherTest {

	public void testDoesNotMatchEmptyArray() {
		String[] array = {};
		assertDoesNotMatch("empty collection", isArrayOnlyContaining("foo"), array);
	}
	
	public void testMatchesSingletonArray() {
		Integer[] array = {1};
		assertMatches("singleton array", isArrayOnlyContaining(1), array);
	}
	
	public void testMatchesArray() {
		Integer[] array = {1,2,3};
		assertMatches("array", isArrayOnlyContaining(1,2,3), array);
	}
	
	public void testDoesNotMatchArrayWithMismatchingItem() {		
		Integer[] array = {1,2,3};
		assertDoesNotMatch("array with mismatching item",
				isArrayOnlyContaining(1,2), array);
	}
	
	@Override
	protected Matcher<?> createMatcher() {		
		return isArrayOnlyContaining("foo");
	}
}
