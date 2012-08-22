
package org.hamcrest.date;

import java.util.Calendar;
import java.util.Date;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * A matcher that tests that the examined date is on the same second as the reference date
 * 
 * @author Stewart Bissett
 */
public class IsSameSecond extends AbstractDatePartMatcher {

	public IsSameSecond(final Date date) {
		super(date, Calendar.SECOND, "second");
	}

	/**
	 * Creates a matcher that matches when the examined date is on the same second as the reference date
	 * <p/>
	 * For example:
	 * 
	 * <pre>
	 * assertThat(myDate, sameSecond(new Date()))
	 * </pre>
	 * 
	 * @param date the reference date against which the examined date is checked
	 */
	@Factory
	public static Matcher<Date> sameSecond(final Date date) {
		return new IsSameSecond(date);
	}

}