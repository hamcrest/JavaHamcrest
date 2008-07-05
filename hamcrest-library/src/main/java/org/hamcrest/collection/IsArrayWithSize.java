package org.hamcrest.collection;

import static org.hamcrest.core.DescribedAs.describedAs;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matches if array size satisfies a nested matcher.
 */
public class IsArrayWithSize<E> extends TypeSafeMatcher<E[]> {
	private final Matcher<Integer> sizeMatcher;

	public IsArrayWithSize(Matcher<Integer> sizeMatcher) {
		this.sizeMatcher = sizeMatcher;
	}

	@Override
	public boolean matchesSafely(E[] item) {
		return sizeMatcher.matches(item.length);
	}

	public void describeTo(Description description) {
		description.appendText("is array with size ")
			.appendDescriptionOf(sizeMatcher);
	}

    /**
     * Does array size satisfy a given matcher?
     */
	@Factory
	public static <E> Matcher<E[]> arrayWithSize(Matcher<Integer> sizeMatcher) {
		return new IsArrayWithSize<E>(sizeMatcher);
	}

    /**
     * This is a shortcut to the frequently used arrayWithSize(equalTo(x)).
     *
     * For example,  assertThat(arrayWithSize(equal_to(x)))
     *          vs.  assertThat(arrayWithSize(x))
     */
	@Factory
	public static <E> Matcher<E[]> arrayWithSize(int size) {
		return arrayWithSize(equalTo(size));
	}

    /**
     * Matches an empty array.
     */
	@Factory
	public static <E> Matcher<E[]> emptyArray() {
		Matcher<E[]> isEmpty = arrayWithSize(0);
		return describedAs("is an empty array", isEmpty);
	}
}
