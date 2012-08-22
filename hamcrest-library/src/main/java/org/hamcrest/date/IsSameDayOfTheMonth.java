
package org.hamcrest.date;

import java.util.Calendar;
import java.util.Date;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * A matcher that tests that the examined date is on the same day of the month as the reference date
 * 
 * @author Stewart Bissett
 */
public class IsSameDayOfTheMonth extends AbstractDatePartMatcher {

	public IsSameDayOfTheMonth(final Date date) {
		super(date, Calendar.DATE, "day of the month");
	}

	/**
	 * Creates a matcher that matches when the examined date is on the same day of the month as the reference date
	 * <p/>
	 * For example:
	 * 
	 * <pre>
	 * assertThat(myDate, sameDayOfTheMonth(new Date()))
	 * </pre>
	 * 
	 * @param date the reference date against which the examined date is checked
	 */
	@Factory
	public static Matcher<Date> sameDayOfTheMonth(final Date date) {
		return new IsSameDayOfTheMonth(date);
	}

}