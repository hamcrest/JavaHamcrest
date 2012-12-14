
package org.hamcrest.date;

import java.util.Calendar;
import java.util.Date;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * A matcher that tests that the examined date is on the same month as the reference date
 * 
 * @author Stewart Bissett
 */
public class IsSameMonth extends AbstractDatePartMatcher {

	public IsSameMonth(final Date date) {
		super(date, Calendar.MONTH, "month");
	}

	/**
	 * Creates a matcher that matches when the examined date is on the same month as the reference date
	 * <p/>
	 * For example:
	 * 
	 * <pre>
	 * assertThat(myDate, sameMonth(new Date()))
	 * </pre>
	 * 
	 * @param date the reference date against which the examined date is checked
	 */
	@Factory
	public static Matcher<Date> sameMonth(final Date date) {
		return new IsSameMonth(date);
	}

}