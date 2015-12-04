package org.hamcrest.core;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.core.TraversedAs.traversedAs;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static java.util.Arrays.*;

public class TraversedAsTest {
  
  
  @Test
  public void success() {
    List<Integer> actual = Collections.singletonList(1);
    assertThat(actual, traversedAs(asList(1)));
  }
  
  @Test
  public void firstElemDifferenceFailure() {
    List<Integer> actual = asList(1);
    assertThat(actual, is(not(traversedAs(asList(2)))));
  }

}
