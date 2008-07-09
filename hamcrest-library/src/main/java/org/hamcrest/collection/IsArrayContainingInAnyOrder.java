package org.hamcrest.collection;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsArrayContainingInAnyOrder<E> extends TypeSafeMatcher<E[]> {
	private final IsIterableContainingInAnyOrder<E> iterableMatcher;
	private final Collection<Matcher<? super E>> matchers;

	public IsArrayContainingInAnyOrder(Collection<Matcher<? super E>> matchers) {
		this.iterableMatcher = new IsIterableContainingInAnyOrder<E>(matchers);
		this.matchers = matchers;
	}

	@Override
	public boolean matchesSafely(E[] item) {
		return iterableMatcher.matches(Arrays.asList(item));
	}

	public void describeTo(Description description) {
		description.appendList("[", ", ", "]", matchers)
            .appendText(" in any order");
	}

	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E>... matchers) {
		return arrayContainingInAnyOrder(Arrays.asList(matchers));
	}


	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(Collection<Matcher<? super E>> matchers) {
		return new IsArrayContainingInAnyOrder<E>(matchers);
	}

	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(E... items) {
		List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
		for (E item : items) {
			matchers.add(equalTo(item));
		}
		return new IsArrayContainingInAnyOrder<E>(matchers);
	}

	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(E first, E second) {
		List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
		matchers.add(equalTo(first));
		matchers.add(equalTo(second));
		return new IsArrayContainingInAnyOrder<E>(matchers);
	}

	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(E first, E second, E third) {
		List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
		matchers.add(equalTo(first));
		matchers.add(equalTo(second));
		matchers.add(equalTo(third));
		return new IsArrayContainingInAnyOrder<E>(matchers);
	}

	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(E first, E second, E third, E forth) {
		List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
		matchers.add(equalTo(first));
		matchers.add(equalTo(second));
		matchers.add(equalTo(third));
		matchers.add(equalTo(forth));
		return new IsArrayContainingInAnyOrder<E>(matchers);
	}

	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(E first, E second, E third, E forth, E fifth) {
		List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
		matchers.add(equalTo(first));
		matchers.add(equalTo(second));
		matchers.add(equalTo(third));
		matchers.add(equalTo(forth));
		matchers.add(equalTo(fifth));
		return new IsArrayContainingInAnyOrder<E>(matchers);
	}

	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(E first, E second, E third, E forth, E fifth, E sixth) {
		List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
		matchers.add(equalTo(first));
		matchers.add(equalTo(second));
		matchers.add(equalTo(third));
		matchers.add(equalTo(forth));
		matchers.add(equalTo(fifth));
		matchers.add(equalTo(sixth));
		return new IsArrayContainingInAnyOrder<E>(matchers);
	}

	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<E> first, Matcher<? super E> second) {
		List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
		matchers.add(first);
		matchers.add(second);
		return arrayContainingInAnyOrder(matchers);
	}

	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third) {
		List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
		matchers.add(first);
		matchers.add(second);
		matchers.add(third);
		return arrayContainingInAnyOrder(matchers);
	}

	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third, Matcher<? super E> forth) {
		List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
		matchers.add(first);
		matchers.add(second);
		matchers.add(third);
		matchers.add(forth);
		return arrayContainingInAnyOrder(matchers);
	}

	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third, Matcher<? super E> forth, Matcher<? super E> fifth) {
		List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
		matchers.add(first);
		matchers.add(second);
		matchers.add(third);
		matchers.add(forth);
		matchers.add(fifth);
		return arrayContainingInAnyOrder(matchers);
	}

	@Factory
	public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<E> first, Matcher<? super E> second, Matcher<? super E> third, Matcher<? super E> forth, Matcher<? super E> fifth, Matcher<? super E> sixth) {
		List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
		matchers.add(first);
		matchers.add(second);
		matchers.add(third);
		matchers.add(forth);
		matchers.add(fifth);
		matchers.add(sixth);
		return arrayContainingInAnyOrder(matchers);
	}
}
