package org.hamcrest.beans;

import java.util.function.Function;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

/**
 * <p>
 * Property matcher like {@link Matchers#hasProperty(String, Matcher)} that instead of using reflection it uses a
 * {@link Function}. This is useful when you wish to assert multiple values of the bean within an assertion statement
 * (which in some projects is the required number of assertions per test).
 * </p>
 *
 * <h2>Example Usage</h2> Consider the situation where we have a class representing a person, with first and last name:
 *
 * <pre>
 * public class Person {
 * 	private String firstName;
 * 	private String lastName;
 *
 * 	public Person(String firstName, String lastName) {
 * 		this.firstName = firstName;
 * 		this.lastName = lastName;
 * 	}
 *
 * 	public String getFirstName() {
 * 		return firstName;
 * 	}
 *
 * 	public String getLastName() {
 * 		return lastName;
 * 	}
 * }
 * </pre>
 *
 * And we would like to test a method that swaps the first name with the last name, then the test would could look like:
 *
 * <pre>
 * final Person newPerson = swapNames(person);
 * assertThat(newPerson, both(hasGetterWithValue(Person::getFirstName, is(equalTo(person.getLastName())))).and(hasGetterWithValue(Person::getLastName, is(equalTo(person.getFirstName())))));
 * </pre>
 *
 * @param <T>
 *            type of object to match
 * @param <V>
 *            type of the property to match
 */
public class HasGetterWithValue<T, V> extends TypeSafeMatcher<T> {

	private final Function<T, V> mapper;
	private final Matcher<? super V> matcher;

	private HasGetterWithValue(final Function<T, V> mapper, final Matcher<? super V> matcher) {
		this.mapper = mapper;
		this.matcher = matcher;
	}

	/** {@inheritDoc} */
	@Override
	public void describeTo(final Description description) {
		description.appendText("hasGetterWithValue(")
				.appendDescriptionOf(matcher).appendText(")");
	}

	@Override
	public void describeMismatchSafely(final T item, final Description mismatchDescription) {
		mismatchDescription.appendText(" was ").appendValue(mapper.apply(item));
	}

	/** {@inheritDoc} */
	@Override
	protected boolean matchesSafely(final T item) {
		return matcher.matches(mapper.apply(item));
	}

	/**
	 * Creates a {@link HasGetterWithValue}.
	 *
	 * @param <T>
	 *            type of object to match
	 * @param <V>
	 *            type of the property to match
	 * @param mapper
	 *            maps form the object to its property
	 * @param matcher
	 *            matches the property
	 * @return the matcher created
	 */
	public static final <T, V> Matcher<T> hasGetterWithValue(final Function<T, V> mapper, final Matcher<? super V> matcher) {
		return new HasGetterWithValue<>(mapper, matcher);
	}

}