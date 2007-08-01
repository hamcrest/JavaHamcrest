package org.hamcrest.collection;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.collection.IsMapContainingKey.hasKey;

public class IsMapContainingKeyTest extends AbstractMatcherTest {

	public void testMatchesSingletonMapContainingKey() {
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("a", 1);
		
		assertMatches("Matches single key", hasKey("a"), map);
	}
	
	public void testMatchesMapContainingKey() {
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		
		assertMatches("Matches a", hasKey("a"), map);
		assertMatches("Matches c", hasKey("c"), map);
	}
	
	public void testHasReadableDescription() {
		assertDescription("map with key \"a\"", hasKey("a"));
	}
	
	public void testDoesNotMatchEmptyMap() {
		assertDoesNotMatch("Empty map", hasKey("Foo"), new HashMap<String,Integer>());
	}
	
	public void testDoesNotMatchMapMissingKey() {
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		
		assertDoesNotMatch("Map without matching key", hasKey("d"), map);
	}
	
	
	@Override
	protected Matcher<?> createMatcher() {
		return hasKey("foo");
	}

}
