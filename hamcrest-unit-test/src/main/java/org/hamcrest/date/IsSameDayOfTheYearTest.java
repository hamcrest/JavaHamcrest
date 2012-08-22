/*
 * Copyright (c) Modular IT Limited.
 */

package org.hamcrest.date;

import static java.util.Calendar.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.date.AbstractDateMatcherTestUtils.*;
import static org.hamcrest.date.IsSameDayOfTheYear.*;
import java.util.Date;
import org.junit.Test;

/**
 * Unit Tests for the {@link IsSameDayOfTheYear} class
 * 
 * @author Stewart Bissett
 */
public class IsSameDayOfTheYearTest {

	@Test
	public void canCompareTheSameDayOfYear() {
		Date date = new Date(), other = new Date(date.getTime());
		assertThat(other, sameDayOfTheYear(date));
	}

	@Test
	public void canDetectDifferentDaysOfTheYear() {
		Date date = new Date(), other = addDateField(date, DAY_OF_YEAR, 1);
		assertThat(other, not(sameDayOfTheYear(date)));
	}

}
