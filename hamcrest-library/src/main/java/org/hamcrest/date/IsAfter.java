
package org.hamcrest.date;

import static org.hamcrest.date.DateFormatter.*;
import java.util.Date;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher that tests that the examined date is after the reference date
 * 
 * @author Stewart Bissett
 */
public class IsAfter extends TypeSafeDiagnosingMatcher<Date> {

	private final Date expected;

	public IsAfter(final Date expected) {
		this.expected = expected;
	}

	@Override
	protected boolean matchesSafely(final Date actual, final Description mismatchDesc) {
		if (expected.compareTo(actual) >= 0) {
			mismatchDesc.appendText("date is ").appendValue(formatDateWithMillis(actual));
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void describeTo(final Description description) {
		description.appendText("the date is after ").appendValue(formatDateWithMillis(expected));
	}

	/**
	 * Creates a matcher that matches when the examined date is after the reference date
	 * <p/>
	 * For example:
	 * 
	 * <pre>
	 * assertThat(myDate, after(new Date()))
	 * </pre>
	 * 
	 * @param date the reference date against which the examined date is checked
	 */
	@Factory
	public static Matcher<Date> after(final Date date) {
		return new IsAfter(date);
	}

}