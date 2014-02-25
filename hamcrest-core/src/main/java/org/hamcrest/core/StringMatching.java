/**
 * 
 */
package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * @author borettim
 * 
 */
public class StringMatching extends SubstringMatcher {

	protected StringMatching(String substring) {
		super(substring);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hamcrest.core.SubstringMatcher#evalSubstringOf(java.lang.String)
	 */
	@Override
	protected boolean evalSubstringOf(String s) {
		return s.matches(substring);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hamcrest.core.SubstringMatcher#relationship()
	 */
	@Override
	protected String relationship() {
		return "matching the regex";
	}

	/**
	 * Validate a string with a regex.
	 * 
	 * <pre>
	 * assertThat("abc",matches("^[a-z]+$"));
	 * </pre>
	 * 
	 * @param regex
	 *            The regex to be used for the validation.
	 * @return The matcher.
	 */
	@Factory
	public static Matcher<String> matches(String regex) {
		return new StringMatching(regex);
	}

}
