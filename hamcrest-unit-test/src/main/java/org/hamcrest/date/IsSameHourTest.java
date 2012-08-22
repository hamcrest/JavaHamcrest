/*
 * Copyright (c) Modular IT Limited.
 */

package org.hamcrest.date;

import static java.util.Calendar.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.date.AbstractDateMatcherTestUtils.*;
import static org.hamcrest.date.IsSameHour.*;
import java.util.Date;
import org.junit.Test;

/**
 * Unit Tests for the {@link IsSameHour} class
 * 
 * @author Stewart Bissett
 */
public class IsSameHourTest {

	@Test
	public void canCompareTheSameHour() {
		Date date = new Date(), other = new Date(date.getTime());
		assertThat(other, sameHour(date));
	}

	@Test
	public void canDetectDifferentHour() {
		Date date = new Date(), other = addDateField(date, HOUR, 1);
		assertThat(other, not(sameHour(date)));
	}
}
