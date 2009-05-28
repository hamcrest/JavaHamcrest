package org.hamcrest.core;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import org.hamcrest.Matchers;
import org.hamcrest.StringDescription;
import org.junit.Assert;
import org.junit.Test;

public class CombinableTest {
  private static final CombinableMatcher<Integer> EITHER_3_OR_4 = CombinableMatcher.<Integer>either(equalTo(3)).or(equalTo(4));
  private static final CombinableMatcher<Integer> NOT_3_AND_NOT_4 = CombinableMatcher.<Integer>both(not(equalTo(3))).and(not(equalTo(4)));

  @Test
  public void bothAcceptsAndRejects() {
    assertThat(2, NOT_3_AND_NOT_4);
    assertThat(3, not(NOT_3_AND_NOT_4));
  }
  
  @Test
  public void bothCompilesWithEqualTo() {
    assertThat(2, CombinableMatcher.both(not(equalTo(3))).and(not(equalTo(4))));
    assertThat(3, not(CombinableMatcher.both(not(equalTo(3))).and(not(equalTo(4)))));
  }

  @Test
  public void bothCompilesWithIs() {
	assertThat(3, CombinableMatcher.both(is(3)).and(is(Integer.class)));
  }
	
  @Test
  public void acceptsAndRejectsThreeAnds() {
    final CombinableMatcher<? super Integer> tripleAnd = NOT_3_AND_NOT_4.and(equalTo(2));
    assertThat(2, tripleAnd);
    assertThat(3, not(tripleAnd));
  }

  @Test
  public void bothDescribesItself() {
    assertEquals("(not <3> and not <4>)", NOT_3_AND_NOT_4.toString());
    StringDescription mismatch = new StringDescription();

    NOT_3_AND_NOT_4.describeMismatch(3, mismatch);
    assertEquals("was <3>", mismatch.toString());
  }

  @Test
  public void eitherAcceptsAndRejects() {
    assertThat(3, EITHER_3_OR_4);
    assertThat(6, not(EITHER_3_OR_4));
  }

  @Test
  public void acceptsAndRejectsThreeOrs() {
    final CombinableMatcher<Integer> orTriple = EITHER_3_OR_4.or(Matchers
        .greaterThan(10));
    assertThat(11, orTriple);
    assertThat(9, not(orTriple));
  }

  @Test
  public void eitherDescribesItself() {
    Assert.assertEquals("(<3> or <4>)", EITHER_3_OR_4.toString());
    StringDescription mismatch = new StringDescription();

    EITHER_3_OR_4.describeMismatch(6, mismatch);
    Assert.assertEquals("was <6>", mismatch.toString());
  }

  @Test
  public void picksUpTypeFromLeftHandSideOfExpression() {
    assertThat("yellow", CombinableMatcher.both(equalTo("yellow")).and(notNullValue()));
  }
}
