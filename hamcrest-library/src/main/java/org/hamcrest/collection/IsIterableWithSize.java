package org.hamcrest.collection;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Iterator;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsIterableWithSize<E> extends TypeSafeMatcher<Iterable<E>> {
	private final Matcher<Integer> sizeMatcher;

	public IsIterableWithSize(Matcher<Integer> sizeMatcher) {
		this.sizeMatcher = sizeMatcher;
	}

	@Override
	public boolean matchesSafely(Iterable<E> iterable) {
		Iterator<E> iterator = iterable.iterator();
		int size = 0;
		for (; iterator.hasNext(); iterator.next(), size++) {}
		return sizeMatcher.matches(size);
	}

	public void describeTo(Description description) {
		description.appendText("an iterable with size ")
			.appendDescriptionOf(sizeMatcher);
	}

	@Factory
	public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<Integer> sizeMatcher) {
		return new IsIterableWithSize<E>(sizeMatcher);
	}

	@Factory
	public static <E> Matcher<Iterable<E>> iterableWithSize(int size) {
		return iterableWithSize(equalTo(size));
	}
}
