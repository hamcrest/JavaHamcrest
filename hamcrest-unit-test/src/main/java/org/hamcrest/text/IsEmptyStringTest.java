package org.hamcrest.text;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsEmptyStringTest extends AbstractMatcherTest {

  @Override
  protected Matcher<?> createMatcher() {
    return isEmptyOrNullString();
  }

  public void testEmptyOrNullIsNull() {
    assertThat(null, isEmptyOrNullString());
  }


  public void testEmptyIsNotNull() {
    assertThat(null, not(isEmptyString()));
  }

  public void testMatchesEmptyString() {
    assertMatches("empty string", isEmptyString(), "");
    assertMatches("empty string", isEmptyOrNullString(), "");
  }

  public void testDoesNotMatchNonEmptyString() {
    assertDoesNotMatch("non empty string", isEmptyString(), "a");
  }

  public void testHasAReadableDescription() {
    assertDescription("an empty string", isEmptyString());
    assertDescription("(null or an empty string)", isEmptyOrNullString());
  }
}
