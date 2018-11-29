package org.hamcrest.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * @author Steve Freeman 2016 http://www.hamcrest.com
 */
public class StringMatchingTest {
  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test public void
  startsWithFailsWithNullSubstring() {
    thrown.expect(IllegalArgumentException.class);
    startsWith(null);
  }

  @Test public void
  endWithFailsWithNullSubstring() {
    thrown.expect(IllegalArgumentException.class);
    endsWith(null);
  }

  @Test public void
  containsFailsWithNullSubstring() {
    thrown.expect(IllegalArgumentException.class);
    containsString(null);
  }

}
