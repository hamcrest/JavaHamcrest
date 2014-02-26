package org.hamcrest.core;

import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.core.StringMatching.matchesRegex;

import org.hamcrest.Matcher;
import org.junit.Test;

public class StringMatchingTest {

	@Test
	public void testMatchingRegex() {
		Matcher matcher = matchesRegex("^[0-9]+$");
		assertMatches(matcher, "12");
		assertDoesNotMatch(matcher, new Object());
	}
	
	@Test
	public void testNotMatchingRegex() {
		Matcher matcher = matchesRegex("^[0-9]+$");
		assertDoesNotMatch(matcher, "a");
	}

}
