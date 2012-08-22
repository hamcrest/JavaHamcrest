
package org.hamcrest.date;

import java.util.Calendar;
import java.util.Date;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * A matcher that tests that the examined date is on the same year as the reference date
 * 
 * @author Stewart Bissett
 */
public class IsSameYear extends AbstractDatePartMatcher {

	public IsSameYear(final Date date) {
		super(date, Calendar.YEAR, "year");
	}

	/**
	 * Creates a matcher that matches when the examined date is on the same year as the reference date
	 * <p/>
	 * For example:
	 * 
	 * <pre>
	 * assertThat(myDate, sameYear(new Date()))
	 * </pre>
	 * 
	 * @param date the reference date against which the examined date is checked
	 */
	@Factory
	public static Matcher<Date> sameYear(final Date date) {
		return new IsSameYear(date);
	}

}