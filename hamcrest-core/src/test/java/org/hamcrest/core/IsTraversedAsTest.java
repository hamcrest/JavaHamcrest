package org.hamcrest.core;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsTraversedAs.traversedAs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class IsTraversedAsTest {
  
  private ArrayList<Integer> arrayList(Integer... elems) {
    return new ArrayList<Integer>(asList(elems));
  }
  
  @Test
  public void success() {
    List<Integer> actual = arrayList(1, 2, 3);
    assertThat(actual, traversedAs(1, 2, 3));
  }

  @Test
  public void elemDifferenceFailure() {
    List<Integer> actual = arrayList(1, 2);
    assertThat(actual, is(not(traversedAs(1, 3))));
  }
  
  @Test
  public void moreActualElemsThanExpected() {
    List<Integer> actual = arrayList(1, 2, 3);
    assertThat(actual, is(not(traversedAs(1))));
  }
  
  @Test
  public void moreExpectedItemsThanActual() {
    List<Integer> actual = arrayList(1, 2);
    assertThat(actual, is(not(traversedAs(1, 2, 3))));
  }
  
  @Test
  public void expectedContainsNull() {
    List<Integer> actual = arrayList(1, 2, 3);
    assertThat(actual, is(not(traversedAs(1, null, 3))));
  }
  
  @Test
  public void actualContainsNull() {
    List<Integer> actual = arrayList(1, null, 2);
    assertThat(actual, is(not(traversedAs(1, 2, 3))));
  }
  
  @Test
  public void bothContainsNull() {
    List<Integer> actual = arrayList(1, null, null);
    assertThat(actual, is(traversedAs(1, null, null)));
  }
  
  @Test(expected = NullPointerException.class)
  public void expectedNullFails() {
    List<Integer> actual = arrayList(1, null, null);
    List<Integer> expected = null;
    assertThat(actual, is(traversedAs(expected)));
  }
  
  @Test
  public void actualNullFails() {
    assertThat(null, is(not(traversedAs(1, 2))));
  }

}
