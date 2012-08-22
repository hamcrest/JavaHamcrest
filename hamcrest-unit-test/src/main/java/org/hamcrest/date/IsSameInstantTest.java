/*
 * Copyright (c) Modular IT Limited.
 */

package org.hamcrest.date;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.date.IsSameInstant.*;
import java.util.Date;
import org.junit.Test;

/**
 * Unit Tests for the {@link IsSameInstant} class
 * 
 * @author Stewart Bissett
 */
public class IsSameInstantTest {

	@Test
	public void canCompareTheSameInstant() {
		Date date = new Date(), other = new Date(date.getTime());
		assertThat(other, sameInstant(date));
	}

	@Test
	public void canDetectDifferentInstants() {
		Date date = new Date(), other = new Date(date.getTime() + 1);
		assertThat(other, not(sameInstant(date)));
	}
}
