package org.hamcrest.text.pattern.internal.naming;

import junit.framework.TestCase;

public class PathTests extends TestCase {
	public void testCanBeComparedForEquality() {
		assertTrue(new Path("a", "b").equals(new Path("a", "b")));
		assertFalse(new Path("a", "b").equals(new Path("a")));
		assertFalse(new Path("a", "b").equals(new Path("a", "b", "c")));
		assertFalse(new Path("a", "b").equals(new Path("x", "y")));
		assertFalse(new Path("a", "b").equals(null));
		
		assertTrue(new Path().equals(new Path()));
		
		assertTrue(new Path("a", "b").equals(new Path("x", "a", "b").tail()));
	}
	
	public void testReturnsLengthAndComponents() {
		Path abc = new Path("a", "b", "c");
		assertEquals(3, abc.size());
		assertEquals("a", abc.component(0));
		assertEquals("b", abc.component(1));
		assertEquals("c", abc.component(2));
		
		Path xy = new Path("x", "y");
		assertEquals(2, xy.size());
		assertEquals("x", xy.component(0));
		assertEquals("y", xy.component(1));
		
		assertEquals(0, new Path().size());
	}
	
	public void testReturnsHead() {
		assertEquals("a", new Path("a", "b", "c", "d").head());
		assertEquals("x", new Path("x", "y", "z").head());
	}
	
	public void testReturnsTail() {
		assertEquals(new Path("b", "c", "d"), new Path("a", "b", "c", "d").tail());
		assertEquals(new Path("y", "z"), new Path("x", "y", "z").tail());
		assertEquals(new Path(), new Path("a").tail());
	}
	
	public void testParsesPathFromDottedNotation() {
		assertEquals(new Path("a"), Path.parse("a"));
		assertEquals(new Path("a","b","c"), Path.parse("a.b.c"));
	}
	
	public void testReturnsDottedPathNotationFromToString() {
		assertEquals("a", new Path("a").toString());
		assertEquals("a.b.c", new Path("a","b","c").toString());
	}
}
