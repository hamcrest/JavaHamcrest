package org.hamcrest.collection;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matches collections that only contain elements that satisfy a given matcher.
 * </p>
 * For example <pre>[1,2,3]</pre> satisfies onlyContains(lessThan(4))
 * 
 * @author ngd
 */
public class IsCollectionOnlyContaining<T> extends TypeSafeMatcher<Collection<T>> {

	private final Matcher<T> matcher;

	public IsCollectionOnlyContaining(Matcher<T> matcher) {
		this.matcher = matcher;
	}
	
	@Override
	public boolean matchesSafely(Collection<T> items) {		
		for (T item : items) {
			if (!matcher.matches(item)) {
				return false;
			}
		}		
		return true;
	}
	
	public void describeTo(Description description) {
		description
    	   	.appendText("a collection containing items matching ")
    	   	.appendDescriptionOf(matcher);
	}
	
	@Factory
	public static <T> Matcher<Collection<T>> onlyContains(T... items) {
		Collection<Matcher<T>> matchers = new ArrayList<Matcher<T>>();
		for (T item : items) {
			matchers.add(equalTo(item));
		}
		return new IsCollectionOnlyContaining<T>(anyOf(matchers));
	}

	@Factory 
	public static <T> Matcher<Collection<T>> onlyContains(Matcher<T>... matchers) {		
		return new IsCollectionOnlyContaining<T>(anyOf(matchers));
	}
}
