package org.hamcrest.collection;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEmptyCollection<E> extends TypeSafeMatcher<Collection<E>> {
	@Override
	public boolean matchesSafely(Collection<E> item) {
		return item.isEmpty();
	}

	public void describeTo(Description description) {
		description.appendText("an empty collection");
	}

	@Factory
	public static <E> Matcher<Collection<E>> empty() {
		return new IsEmptyCollection<E>();
	}
}
