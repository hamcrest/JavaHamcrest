package org.hamcrest.internal;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

public class Assertion <T> {

	private boolean hasFailed;
	private AssertionError exception;

	public Assertion(T actual, Matcher<? super T> matcher) {
		this("", actual, matcher);
	}

	public Assertion(String reason, T actual, Matcher<? super T> matcher) {
		hasFailed = false;
		try {
		    MatcherAssert.assertThat(reason, actual, matcher);
		} catch(AssertionError e) {
			hasFailed = true;
			exception = e;
		}
	}

	public boolean hasFailed(){
		return hasFailed;
	}
	
	public String getErrorMessage(){
		return exception.getLocalizedMessage();
	}
}
