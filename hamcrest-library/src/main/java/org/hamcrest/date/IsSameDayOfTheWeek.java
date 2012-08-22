
package org.hamcrest.date;

import java.util.Calendar;
import java.util.Date;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * A matcher that tests that the examined date is on the same day of the week as the reference date
 * 
 * @author Stewart Bissett
 */
public class IsSameDayOfTheWeek extends AbstractDatePartMatcher {

	public IsSameDayOfTheWeek(final Date date) {
		super(date, Calendar.DAY_OF_WEEK, "day of the week");
	}

	/**
	 * Creates a matcher that matches when the examined date is on the same day of the week as the reference date
	 * <p/>
	 * For example:
	 * 
	 * <pre>
	 * assertThat(myDate, sameDayOfTheWeek(new Date()))
	 * </pre>
	 * 
	 * @param date the reference date against which the examined date is checked
	 */
	@Factory
	public static Matcher<Date> sameDayOfTheWeek(final Date date) {
		return new IsSameDayOfTheWeek(date);
	}

}