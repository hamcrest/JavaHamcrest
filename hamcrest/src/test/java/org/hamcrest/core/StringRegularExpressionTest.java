package org.hamcrest.core;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.core.StringRegularExpression.matchesRegex;

/**
 * @author Steve Freeman 2016 http://www.hamcrest.com
 */
public class StringRegularExpressionTest extends AbstractMatcherTest {

  public final Matcher<String> matcher = matchesRegex("^[0-9]+$");

  @Override
  protected Matcher<?> createMatcher() { return matcher; }

  @Test
  public void testMatchingRegex() {
    assertMatches(matcher, "12");
    assertDoesNotMatch(matcher, "abc");

    assertDescription("a string matching the pattern <^[0-9]+$>", matcher);
    assertMismatchDescription("the string was \"bcd\"", matcher, "bcd");
  }

}
