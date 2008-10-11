package org.hamcrest.core;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.hamcrest.StringDescription;
import org.junit.Test;

public class EveryTest {
	@Test public void isTrueWhenEveryValueMatches() {
		assertThat(asList("AaA", "BaB", "CaC"), Every.everyItem(containsString("a")));
		assertThat(asList("AbA", "BbB", "CbC"), not(Every.everyItem(containsString("a"))));
	}
	
	@Test public void isAlwaysTrueForEmptyLists() {
		assertThat(new ArrayList<String>(), Every.everyItem(containsString("a")));		
	}
	
	@Test public void describesItself() {
		final Every<String> each=  new Every<String>(containsString("a"));
		assertEquals("every item is a string containing \"a\"", each.toString());
		
		StringDescription description = new StringDescription(); 
		each.matchesSafely(asList("BbB"), description);
		assertEquals("an item was \"BbB\"", description.toString());
	}
}
