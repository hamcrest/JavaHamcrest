/*
 * Copyright (c) Modular IT Limited.
 */

package org.hamcrest.date;

import static java.util.Calendar.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.date.AbstractDateMatcherTestUtils.*;
import static org.hamcrest.date.IsWithin.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * Unit Tests for the {@link IsSameYear} class
 * 
 * @author Stewart Bissett
 */
public class IsWithinTest {

	@Test
	public void matchesEarlierDatesWithinLimit() {
		Date date = new Date(), other = addDateField(date, SECOND, -1);
		assertThat(other, within(2, TimeUnit.SECONDS, date));
	}

	@Test
	public void matchesLaterDatesWithinLimit() {
		Date date = new Date(), other = addDateField(date, SECOND, 1);
		assertThat(other, within(2, TimeUnit.SECONDS, date));
	}

	@Test
	public void doesNotMatchesEarlierDatesOutsideLimit() {
		Date date = new Date(), other = addDateField(date, SECOND, -2);
		assertThat(other, not(within(1, TimeUnit.SECONDS, date)));
	}

	@Test
	public void doesNotMatchesLaterDatesOutsideLimit() {
		Date date = new Date(), other = addDateField(date, SECOND, 2);
		assertThat(other, not(within(1, TimeUnit.SECONDS, date)));
	}

}
