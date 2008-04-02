package org.hamcrest.collection;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsCollectionWithSize<E> extends TypeSafeMatcher<Collection<E>> {
	private final Matcher<Integer> sizeMatcher;

	public IsCollectionWithSize(Matcher<Integer> sizeMatcher) {
		this.sizeMatcher = sizeMatcher;
	}

	@Override
	public boolean matchesSafely(Collection<E> item) {
		return sizeMatcher.matches(item.size());
	}

	public void describeTo(Description description) {
		description.appendText("a collection with size ")
			.appendDescriptionOf(sizeMatcher);
	}

	@Factory
	public static <E> Matcher<Collection<E>> hasSize(int size) {
		return hasSize(equalTo(size));
	}

	@Factory
	public static <E> Matcher<Collection<E>> hasSize(Matcher<Integer> size) {
		return new IsCollectionWithSize<E>(size);
	}
}
