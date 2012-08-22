/*
 * Copyright (c) Modular IT Limited.
 */

package org.hamcrest.date;

import static java.util.Calendar.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.date.AbstractDateMatcherTestUtils.*;
import static org.hamcrest.date.IsSameMinute.*;
import java.util.Date;
import org.junit.Test;

/**
 * Unit Tests for the {@link IsSameMinute} class
 * 
 * @author Stewart Bissett
 */
public class IsSameMinuteTest {

	@Test
	public void canCompareTheSameMinute() {
		Date date = new Date(), other = new Date(date.getTime());
		assertThat(other, sameMinute(date));
	}

	@Test
	public void canDetectDifferentMinute() {
		Date date = new Date(), other = addDateField(date, MINUTE, 1);
		assertThat(other, not(sameMinute(date)));
	}
}
