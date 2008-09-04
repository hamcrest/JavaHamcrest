package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.text.IsEmptyOrNullString.isEmptyString;

public class IsEmptyOrNullStringTest extends AbstractMatcherTest {

  @Override
  protected Matcher<?> createMatcher() {
    return IsEmptyOrNullString.isEmptyOrNullString();
  }

  public void testMatchesNull() {
    assertMatches("null", isEmptyString(), null);
  }

  public void testMatchesEmptyString() {
    assertMatches("empty string", isEmptyString(), "");
  }

  public void testDoesNotMatchNonEmptyString() {
    assertDoesNotMatch("non empty string", isEmptyString(), "a");
  }

  public void testHasAReadableDescription() {
    assertDescription("an empty string or null", isEmptyString());
  }
}
