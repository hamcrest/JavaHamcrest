
package org.hamcrest.date;

import java.util.Calendar;
import java.util.Date;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Base class matching parts of a Date as defined by the constants on {@link Calendar} e.g {@link Calendar#YEAR}
 * 
 * @author Stewart Bissett
 */
abstract class AbstractDatePartMatcher extends TypeSafeDiagnosingMatcher<Date> {

	private final int datePart;
	private final int expected;
	private final Date expectedDate;
	private final String datePartLabel;

	public AbstractDatePartMatcher(final Date date, final int datePart, final String datePartLabel) {
		this.datePart = datePart;
		this.datePartLabel = datePartLabel;
		this.expected = extractDatePart(date, datePart);
		this.expectedDate = date;
	}

	@Override
	protected boolean matchesSafely(final Date actual, final Description mismatchDesc) {
		int actualDatePart = extractDatePart(actual, datePart);
		if (expected != actualDatePart) {
			mismatchDesc.appendText(datePartLabel).appendText(" is ").appendValue(describeDate(actual));
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void describeTo(final Description description) {
		description.appendText("the same ").appendText(datePartLabel).appendText(" as ").appendValue(describeDate(expectedDate));
	}

	private String describeDate(final Date date) {
		return date.toString();
	}

	private int extractDatePart(final Date date, final int part) {
		return convertDateToCalendar(date).get(part);
	}

	private Calendar convertDateToCalendar(final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

}