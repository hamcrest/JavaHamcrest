/**
 * 
 */
package org.hamcrest.core;

import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * @author borettim
 * 
 */
public class StringMatching extends TypeSafeDiagnosingMatcher<String> {

	protected StringMatching(Pattern pattern) {
		this.pattern = pattern;
	}

	private Pattern pattern;

	@Override
	public void describeTo(Description description) {
		description.appendText("a string matching the pattern ").appendValue(pattern);
	}

	@Override
	protected boolean matchesSafely(String item, Description mismatchDescription) {
		if (!pattern.matcher(item).matches()) {
			mismatchDescription.appendText("but the string ").appendValue(item)
					.appendText(" was not matching ").appendValue(pattern);
			return false;
		}
		return true;
	}

	/**
	 * Validate a string with a {@link java.util.regex.Pattern}.
	 * 
	 * <pre>
	 * assertThat(&quot;abc&quot;, matchesRegex(Pattern.compile(&quot;&circ;[a-z]$&quot;));
	 * </pre>
	 * 
	 * @param pattern
	 *            the pattern to be used.
	 * @return The matcher.
	 */
	@Factory
	public static Matcher<String> matchesRegex(Pattern pattern) {
		return new StringMatching(pattern);
	}

	/**
	 * Validate a string with a regex.
	 * 
	 * <pre>
	 * assertThat(&quot;abc&quot;, matchesRegex(&quot;&circ;[a-z]+$&quot;));
	 * </pre>
	 * 
	 * @param regex
	 *            The regex to be used for the validation.
	 * @return The matcher.
	 */
	@Factory
	public static Matcher<String> matchesRegex(String regex) {
		return matchesRegex(Pattern.compile(regex));
	}
}
