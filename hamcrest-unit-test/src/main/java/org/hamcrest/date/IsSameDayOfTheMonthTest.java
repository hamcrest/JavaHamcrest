/*
 * Copyright (c) Modular IT Limited.
 */

package org.hamcrest.date;

import static java.util.Calendar.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.date.AbstractDateMatcherTestUtils.*;
import static org.hamcrest.date.IsSameDayOfTheMonth.*;
import java.util.Date;
import org.junit.Test;

/**
 * Unit Tests for the {@link IsSameDayOfMonth} class
 * 
 * @author Stewart Bissett
 */
public class IsSameDayOfTheMonthTest {

	@Test
	public void canCompareTheSameDayOfMonth() {
		Date date = new Date(), other = new Date(date.getTime());
		assertThat(other, sameDayOfTheMonth(date));
	}

	@Test
	public void canDetectDifferentDaysOfTheMonth() {
		Date date = new Date(), other = addDateField(date, DAY_OF_MONTH, 1);
		assertThat(other, not(sameDayOfTheMonth(date)));
	}

}
