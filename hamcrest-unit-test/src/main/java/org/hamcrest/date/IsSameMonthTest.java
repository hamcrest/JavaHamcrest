/*
 * Copyright (c) Modular IT Limited.
 */

package org.hamcrest.date;

import static java.util.Calendar.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.date.AbstractDateMatcherTestUtils.*;
import static org.hamcrest.date.IsSameMonth.*;
import java.util.Date;
import org.junit.Test;

/**
 * Unit Tests for the {@link IsSameMonth} class
 * 
 * @author Stewart Bissett
 */
public class IsSameMonthTest {

	@Test
	public void canCompareTheSameMonth() {
		Date date = new Date(), other = new Date(date.getTime());
		assertThat(other, sameMonth(date));
	}

	@Test
	public void canDetectDifferentMonths() {
		Date date = new Date(), other = addDateField(date, MONTH, 1);
		assertThat(other, not(sameMonth(date)));
	}
}
