package org.hamcrest.collection;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsIterableContainingParallelRuns.containsParallelRunsOf;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.List;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class IsIterableContainingParallelRunsTest extends AbstractMatcherTest {

  @Override
  protected Matcher<?> createMatcher() {
    return containsParallelRunsOf(1, 1, 2);
  }

  //
  // ---- SINGLE RUN TESTS ---- (same tests cases as IsIterableContainingInRelativeOrderTest) ----
  //

  public void testMatchingSingleItemIterable() {
    assertMatches("Single item iterable",
        containsParallelRunsOf(1, 1), singletonList(1));
  }

  public void testMatchingMultipleItemIterable() {
    assertMatches("Multiple item iterable",
        containsParallelRunsOf(1, 1, 2, 3), asList(1, 2, 3));
  }

  public void testMatchesWithMoreElementsThanExpectedAtBeginning() {
    assertMatches("More elements at beginning",
        containsParallelRunsOf(1, 2, 3, 4), asList(1, 2, 3, 4));
  }

  public void testMatchesWithMoreElementsThanExpectedAtEnd() {
    assertMatches("More elements at end",
        containsParallelRunsOf(1, 1, 2, 3), asList(1, 2, 3, 4));
  }

  public void testMatchesWithMoreElementsThanExpectedInBetween() {
    assertMatches("More elements in between",
        containsParallelRunsOf(1, 1, 3), asList(1, 2, 3));
  }

  public void testMatchesSubSection() {
    assertMatches("Sub section of iterable",
        containsParallelRunsOf(1, 2, 3), asList(1, 2, 3, 4));
  }

  public void testMatchesWithSingleGapAndNotFirstOrLast() {
    assertMatches("Sub section with single gaps without a first or last match",
        containsParallelRunsOf(1, 2, 4), asList(1, 2, 3, 4, 5));
  }

  public void testMatchingSubSectionWithManyGaps() {
    assertMatches("Sub section with many gaps iterable",
        containsParallelRunsOf(1, 2, 4, 6), asList(1, 2, 3, 4, 5, 6, 7));
  }

  public void testDoesNotMatchWithFewerElementsThanExpected() {
    List<WithValue> valueList = asList(make(1), make(2));
    assertMismatchDescription("value with <3> was not found after <WithValue 2>",
        containsParallelRunsOf(1, value(1), value(2), value(3)), valueList);
  }

  public void testDoesNotMatchIfSingleItemNotFound() {
    assertMismatchDescription("value with <4> was not found",
        containsParallelRunsOf(1, value(4)), singletonList(make(3)));
  }

  public void testDoesNotMatchIfOneOfMultipleItemsNotFound() {
    assertMismatchDescription("value with <3> was not found after <WithValue 2>",
        containsParallelRunsOf(1, value(1), value(2), value(3)),
        asList(make(1), make(2), make(4)));
  }

  public void testDoesNotMatchEmptyIterable() {
    assertMismatchDescription("value with <4> was not found",
        containsParallelRunsOf(1, value(4)), emptyList());
  }

  public void testHasAReadableDescription() {
    assertDescription(
        "iterable containing [<1>, <2>] in relative order",
        containsParallelRunsOf(1, 1, 2));
  }

  //
  // ---- MULTIPLE PARALLEL RUN TESTS ------------------------------------------------------------
  //

  public void testMultiMatchesWithoutUnexpectedElements() {
    assertMatches("Multiple runs without unexpected elements",
        containsParallelRunsOf(2, 1, 2, 3), asList(1, 1, 2, 3, 2, 3));
  }

  public void testMultiMatchesWithRepeatedElements() {
    assertMatches("Multiple runs with repeated elements",
        containsParallelRunsOf(2, 1, 2, 1), asList(1, 2, 1, 1, 2, 1));
  }

  public void testMultiMatchesWithGaps() {
    assertMatches("Multiple runs with gaps",
        containsParallelRunsOf(4, 1), asList(2, 1, 2, 1, 1, 2, 1, 2));
  }

  public void testMultiDoesNotMatchIfSingleItemNotFound() {
    assertMismatchDescription("value with <2> was not found after <WithValue 1> in run <2>",
        containsParallelRunsOf(2, value(1), value(2)),
        asList(make(1), make(2), make(1)));
  }

  public void testMultiDoesNotMatchIfSingleItemNotFoundAtStart() {
    assertMismatchDescription("value with <1> was not found in run <2>",
        containsParallelRunsOf(2, value(1), value(2)),
        asList(make(1), make(2)));
  }

  public void testMultiDoesNotMatchAndReportsAllMismatchedRuns() {
    assertMismatchDescription("<3> was not found after <2> in run <1>; and "
            + "<2> was not found after <1> in run <2>; and "
            + "<1> was not found in run <3>",
        containsParallelRunsOf(3, 1, 2, 3),
        asList(1, 1, 2));
  }

  public void testMultiDoesNotMatchEmptyIterable() {
    assertMismatchDescription("value with <4> was not found in run <1>; and "
            + "value with <4> was not found in run <2>",
        containsParallelRunsOf(2, value(4)), emptyList());
  }

  public void testMultiHasAReadableDescription() {
    assertDescription(
        "iterable containing <2> parallel runs of [<1>, <2>] in relative order",
        containsParallelRunsOf(2, 1, 2));
    assertDescription(
        "iterable containing <901> parallel runs of [<1>, <2>] in relative order",
        containsParallelRunsOf(901, 1, 2));
  }

  // ---- TEST UTILITIES -------------------------------------------------------------------------

  public static class WithValue {
    private final int value;
    public WithValue(int value) { this.value = value; }
    public int getValue() { return value; }
    @Override public String toString() { return "WithValue " + value; }
  }

  public static WithValue make(int value) {
    return new WithValue(value);
  }

  public static Matcher<WithValue> value(int value) {
    return new FeatureMatcher<WithValue, Integer>(equalTo(value), "value with", "value") {
      @Override
      protected Integer featureValueOf(WithValue actual) {
        return actual.getValue();
      }
    };
  }

}
