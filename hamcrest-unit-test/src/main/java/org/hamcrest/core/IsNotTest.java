/*  Copyright (c) 2000-2009 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.number.OrderingComparison.greaterThan;

public class IsNotTest extends AbstractMatcherTest {
  @Override
  protected Matcher<?> createMatcher() {
    return not("something");
  }

  public void testEvaluatesToTheTheLogicalNegationOfAnotherMatcher() {
    assertMatches("should match", not(equalTo("A")), "B");
    assertDoesNotMatch("should not match", not(equalTo("B")), "B");
  }

  public void testProvidesConvenientShortcutForNotEqualTo() {
    assertMatches("should match", not("A"), "B");
    assertMatches("should match", not("B"), "A");
    assertDoesNotMatch("should not match", not("A"), "A");
    assertDoesNotMatch("should not match", not("B"), "B");
  }

  public void testUsesDescriptionOfNegatedMatcherWithPrefix() {
    assertDescription("not a value greater than <2>", not(greaterThan(2)));
    assertDescription("not \"A\"", not("A"));
  }
 }
