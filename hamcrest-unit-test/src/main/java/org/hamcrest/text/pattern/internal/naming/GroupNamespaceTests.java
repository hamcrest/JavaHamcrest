package org.hamcrest.text.pattern.internal.naming;

import org.hamcrest.text.pattern.internal.naming.GroupNamespace;
import org.hamcrest.text.pattern.internal.naming.Path;

import junit.framework.TestCase;

public class GroupNamespaceTests extends TestCase {
	GroupNamespace environment = new GroupNamespace();
	
	public void testBindsIndicesToNamesAllocatingIndicesFromOne() {
		environment.create("a");
		environment.create("b");
		
		assertEquals(1, (int)environment.resolve("a"));
		assertEquals(2, (int)environment.resolve("b"));
	}
	
	public void testThrowsIllegalArgumentExceptionIfNameNotBound() {
		try {
			environment.resolve("not bound");
			fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException e) {
			// expected
		}
	}

	public void testThrowsIllegalArgumentExceptionIfDuplicateNameBound() {
		environment.create("a");
		try {
			environment.create("a");
			fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException e) {
			// expected
		}
	}
	
	public void testLooksForNameInParentEnvironmentIfNotFoundLocally() {
		environment.create("a");
		GroupNamespace b = environment.create("b");
		
		assertEquals(environment.resolve("a"), b.resolve("a"));
	}
	
	public void testAllocatesIndicesForAndResolvesPathsWithinChildEnvironments() {
		GroupNamespace a = environment.create("a");		
		a.create("x");
		a.create("y");
		GroupNamespace b = environment.create("b");
		b.create("x");
		b.create("y");
		
		assertEquals(2, environment.resolve(new Path("a","x")));
		assertEquals(3, environment.resolve(new Path("a","y")));
		assertEquals(5, environment.resolve(new Path("b","x")));
		assertEquals(6, environment.resolve(new Path("b","y")));
		
		assertEquals(environment.resolve(new Path("b","y")), 
		             a.resolve(new Path("b","y")));
	}
	
	public void testThrowsIllegalArgumentExceptionIfMiddleOfPathNotBound() {
		environment.create("a");
		
		try {
			environment.resolve(new Path("a", "b", "c"));
			fail("should have thrown IllegalArgumentException");
		}
		catch (IllegalArgumentException e) {
			// expected
		}
	}
}
