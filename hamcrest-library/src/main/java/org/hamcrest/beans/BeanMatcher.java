package org.hamcrest.beans;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * @author Sandro Mancuso
 */
public class BeanMatcher<T> extends BaseMatcher<T> {
	
	private BeanPropertyMatcher<?>[] propertyMatchers;
	private Description expectedDescription = new StringDescription();
	private Description mismatchDescription = new StringDescription();

	public BeanMatcher(BeanPropertyMatcher<?>... propertyMatchers) {
		this.propertyMatchers = propertyMatchers;
	}

	@Factory
	public static <T> BeanMatcher<T> has(BeanPropertyMatcher<?>... propertyMatchers) {
		return new BeanMatcher<T>(propertyMatchers);
	}
	
	public boolean matches(Object item) {
		boolean matches = true;
		for (BeanPropertyMatcher<?> matcher : propertyMatchers) {
			if (!matcher.matches(item)) {
				matches = false;
				appendDescriptions(item, matcher);
			}
		}
		return matches;
	}

	public void describeTo(Description description) {
		description.appendText(expectedDescription.toString());
	}

	@Override
	public void describeMismatch(Object item, Description description) {
		description.appendText(mismatchDescription.toString());
	}

	private void appendDescriptions(Object item, Matcher<?> matcher) {
		matcher.describeTo(expectedDescription);
		matcher.describeMismatch(item, mismatchDescription);
	}
	
}
