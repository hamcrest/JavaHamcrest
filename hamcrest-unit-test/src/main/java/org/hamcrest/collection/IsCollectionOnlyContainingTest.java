package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.collection.IsCollectionOnlyContaining.onlyContains;

public class IsCollectionOnlyContainingTest extends AbstractMatcherTest {

	public void testDoesNotMatchEmptyCollection() {
		Collection<String> collection = new ArrayList<String>();
		assertDoesNotMatch("empty collection", onlyContains("foo"), collection);
	}
	
	public void testMatchesSingletonCollection() {
		Collection<Integer> collection = new ArrayList<Integer>();
		collection.add(1);
		assertMatches("singleton collection", onlyContains(1), collection);
	}
	
	public void testMatchesCollection() {
		Collection<Integer> collection = new ArrayList<Integer>();
		collection.add(1);
		collection.add(2);
		assertMatches("collection", onlyContains(1,2), collection);
	}
	
	public void testDoesNotMatchCollectionWithMismatchingItem() {		
		Collection<Integer> collection = new ArrayList<Integer>();
		collection.add(1);
		collection.add(2);
		collection.add(3);
		assertDoesNotMatch("collection", onlyContains(1,2), collection);
	}
	
	@Override
	protected Matcher<?> createMatcher() {
		return onlyContains(1,2,3);
	}

}
