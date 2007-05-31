package org.hamcrest.text.pattern.internal.naming;

public class IndexSequence {
	private int next = 0;
	
	public int next() {
		return next++;
	}
}
