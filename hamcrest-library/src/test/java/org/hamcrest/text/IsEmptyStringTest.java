package org.hamcrest.text;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.hamcrest.text.IsEmptyString.emptyString;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class IsEmptyStringTest extends AbstractMatcherTest {

  @Override
  protected Matcher<?> createMatcher() {
    return emptyOrNullString();
  }

  public void testEmptyOrNullIsNull() {
    assertThat(null, emptyOrNullString());
  }


  public void testEmptyIsNotNull() {
    assertThat(null, not(emptyString()));
  }

  public void testMatchesEmptyString() {
    assertMatches("empty string", emptyString(), "");
    assertMatches("empty string", emptyOrNullString(), "");
  }

  public void testDoesNotMatchNonEmptyString() {
    assertDoesNotMatch("non empty string", emptyString(), "a");
  }

  public void testHasAReadableDescription() {
    assertDescription("an empty string", emptyString());
    assertDescription("(null or an empty string)", emptyOrNullString());
  }
}
