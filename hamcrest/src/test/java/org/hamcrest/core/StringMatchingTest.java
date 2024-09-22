package org.hamcrest.core;

import org.junit.jupiter.api.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Steve Freeman 2016 http://www.hamcrest.com
 */
public class StringMatchingTest {

  @Test public void
  startsWithFailsWithNullSubstring() {
    assertThrows(IllegalArgumentException.class, () -> {
      startsWith(null);
    });
  }

  @Test public void
  endWithFailsWithNullSubstring() {
    assertThrows(IllegalArgumentException.class, () -> {
      endsWith(null);
    });
  }

  @Test public void
  containsFailsWithNullSubstring() {
    assertThrows(IllegalArgumentException.class, () -> {
      containsString(null);
    });
  }

}
