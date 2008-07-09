package org.hamcrest.collection;

import java.util.Iterator;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.core.IsEqual.equalTo;

public class IsIterableContainingSingleItem<E> extends TypeSafeMatcher<Iterable<E>> {
	private final Matcher<? super E> matcher;
	
	public IsIterableContainingSingleItem(Matcher<? super E> matcher) {
		this.matcher = matcher;
	}
	
	@Override
	public boolean matchesSafely(Iterable<E> iterable) {
		Iterator<E> items = iterable.iterator();
		if (!items.hasNext()) {
			return false;
		}
		return matcher.matches(items.next()) && !items.hasNext();
	}

	public void describeTo(Description description) {
		description.appendText("a singleton iterable with [")
			.appendDescriptionOf(matcher)
			.appendText("]");
	}
	
	@Factory
	public static <E> Matcher<Iterable<E>> hasSingleItem(Matcher<? super E> matcher) {
		return new IsIterableContainingSingleItem<E>(matcher);
	}
	
	@Factory
	public static <E> Matcher<Iterable<E>> hasSingleItem(E item) {
		return hasSingleItem(equalTo(item));		
	}
}
