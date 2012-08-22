
package org.hamcrest.date;

import static org.hamcrest.date.DateFormatter.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher that tests that the examined date is within a defined period of the reference date
 * 
 * @author Stewart Bissett
 */
public class IsWithin extends TypeSafeDiagnosingMatcher<Date> {

	private final long period;
	private final TimeUnit unit;
	private final Date expected;
	private final long expectedDifferenceInMillis;

	public IsWithin(final long period, final TimeUnit unit, final Date expected) {
		this.period = period;
		this.unit = unit;
		this.expected = expected;
		this.expectedDifferenceInMillis = unit.toMillis(period);
	}

	@Override
	protected boolean matchesSafely(final Date actual, final Description mismatchDesc) {
		long differenceInMillis = Math.abs(expected.getTime() - actual.getTime());
		if (differenceInMillis > expectedDifferenceInMillis) {
			mismatchDesc.appendText("date is ").appendValue(formatDateWithMillis(actual));
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void describeTo(final Description description) {
		description.appendText("the date is within " + period + " " + abbreviatedUnit(unit) + " of ").appendValue(formatDateWithMillis(expected));
	}

	private String abbreviatedUnit(final TimeUnit unit) {
		switch (unit) {
			case MICROSECONDS:
				return "µs";
			case MILLISECONDS:
				return "ms";
			case MINUTES:
				return "mins";
			case NANOSECONDS:
				return "ns";
			case SECONDS:
				return "secs";
			case DAYS:
			case HOURS:
			default:
				return unit.name().toLowerCase();
		}
	}

	/**
	 * Creates a matcher that matches when the examined date is within a defined period the reference date
	 * <p/>
	 * For example:
	 * 
	 * <pre>
	 * assertThat(myDate, within(10, TimeUnit.MINUTES, new Date()))
	 * </pre>
	 * 
	 * @param date the reference date against which the examined date is checked
	 */
	@Factory
	public static Matcher<Date> within(final long period, final TimeUnit unit, final Date date) {
		return new IsWithin(period, unit, date);
	}
}